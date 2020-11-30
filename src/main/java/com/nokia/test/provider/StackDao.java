package com.nokia.test.provider;

import com.nokia.test.model.Stack;
import com.nokia.test.repo.StackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StackDao {

    @Autowired
    private StackRepo stackRepo;

    public Stack popFromStack() {
        Optional<Stack> stack = stackRepo.findTopByOrderByIdDesc();
        if (stack.isPresent()) {
            stackRepo.deleteById(stack.get().getId());
            return stack.get();
        }
        return null;
    }

    public void pushToStack(Stack stack) {
        stackRepo.save(stack);
    }

    public long getStackCurrentCapacity() {
        Optional<Stack> optionalStack = stackRepo.findTopByOrderByIdDesc();
        if (!optionalStack.isPresent()) {
            return 0;
        }
        return optionalStack.get().getId();
    }
}