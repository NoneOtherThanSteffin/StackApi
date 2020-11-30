package com.nokia.test.service;

import com.nokia.test.exception.StackEmptyException;
import com.nokia.test.exception.StackException;
import com.nokia.test.exception.StackFullException;
import com.nokia.test.model.Stack;
import com.nokia.test.model.StackApiRequest;
import com.nokia.test.model.StackApiResponse;
import com.nokia.test.provider.StackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StackService {

    @Value("${stack.max.capacity}")
    private Long stackCapacity;

    @Autowired
    private StackDao stackDao;

    public void push(StackApiRequest stackApiRequest) throws StackFullException, StackException {
        if (stackApiRequest == null || stackApiRequest.getData() == null) {
            throw new StackException("Request is not valid");
        }
        long stackMaxValue = stackDao.getStackCurrentCapacity();
        if (stackMaxValue == stackCapacity) {
            throw new StackFullException("Stack Is Full");
        }
        String data = stackApiRequest.getData();
        Stack stack = new Stack(stackMaxValue + 1, data);
        try {
            stackDao.pushToStack(stack);
        } catch (DataAccessException dxe) {
            throw new StackException(dxe.getMessage());
        }
    }

    public StackApiResponse pop() throws StackEmptyException {
        Stack stack = stackDao.popFromStack();
        if (stack == null) {
            throw new StackEmptyException("Stack Is Empty");
        }
        StackApiResponse stackApiResponse = new StackApiResponse();
        stackApiResponse.setData(stack.getData());
        return stackApiResponse;
    }
}