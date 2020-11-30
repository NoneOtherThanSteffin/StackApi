package com.nokia.test;

import com.nokia.test.exception.StackEmptyException;
import com.nokia.test.exception.StackException;
import com.nokia.test.exception.StackFullException;
import com.nokia.test.model.StackApiRequest;
import com.nokia.test.model.StackApiResponse;
import com.nokia.test.service.StackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class StackServiceTests {

    @Mock
    private StackService stackService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(stackService, "stackCapacity", 5L, Long.class);
    }

    @Test
    public void stackPush() throws StackFullException, StackException {
        StackApiRequest stackApiRequest = new StackApiRequest();
        stackApiRequest.setData("1");
        doNothing().when(stackService).push(stackApiRequest);
        stackService.push(stackApiRequest);
    }

    @Test
    public void stackPop() throws StackEmptyException {
        StackApiResponse stackApiResponse = new StackApiResponse();
        stackApiResponse.setData("1");
        when(stackService.pop()).thenReturn(stackApiResponse);

        StackApiRequest stackApiRequest = new StackApiRequest();
        stackApiRequest.setData(stackApiResponse.getData());
        StackApiResponse stackReturn = stackService.pop();
        AssertionErrors.assertNotNull("Stack Value Returned is Empty", stackReturn);
    }

    @Test
    public void shouldThrowStackFullException() throws StackFullException, StackException {
        StackApiRequest stackApiRequest = new StackApiRequest();
        stackApiRequest.setData("1");

        doThrow(new StackFullException("Stack is Full"))
                .when(stackService).push(stackApiRequest);

        Assertions.assertThrows(StackFullException.class,
                () -> stackService.push(stackApiRequest),
                "Expected Exception 'Stack is Full'");
    }

    @Test
    public void shouldThrowStackEmptyException() throws StackEmptyException {
        StackApiRequest stackApiRequest = new StackApiRequest();
        stackApiRequest.setData("1");

        doThrow(new StackEmptyException("Stack is Empty"))
                .when(stackService).pop();

        Assertions.assertThrows(StackEmptyException.class,
                () -> stackService.pop(),
                "Expected Exception 'Stack is Empty'");
    }


}
