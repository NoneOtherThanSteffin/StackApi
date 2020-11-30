package com.nokia.test.api;

import com.nokia.test.exception.ExceptionResponse;
import com.nokia.test.exception.StackEmptyException;
import com.nokia.test.exception.StackException;
import com.nokia.test.exception.StackFullException;
import com.nokia.test.model.StackApiRequest;
import com.nokia.test.model.StackApiResponse;
import com.nokia.test.service.StackService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
@Api(value = "StackApi")
public class StackApi {

    @Autowired
    private StackService stackService;

    @ApiOperation(value = "Pushes given data to stack")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Inserted Given Data"),
            @ApiResponse(code = 507, message = "Stack is full", response = ExceptionResponse.class),
            @ApiResponse(code = 406, message = "Bad request", response = ExceptionResponse.class)})
    @PostMapping("/push")
    public void push(@ApiParam(value = "Data which needs to be pushed to stack")
                         @RequestBody StackApiRequest stackApiRequest) throws StackFullException, StackException {
        stackService.push(stackApiRequest);
    }

    @ApiOperation(value = "Pops data from the stack")
    @ApiResponses(value = {@ApiResponse(code = 406, message = "Illegal Operation - Stack is Empty", response = ExceptionResponse.class)})
    @GetMapping(value = "/pop", produces = "application/json")
    public ResponseEntity<StackApiResponse> get() throws StackEmptyException {
        return ResponseEntity.of(Optional.of(stackService.pop()));
    }
}
