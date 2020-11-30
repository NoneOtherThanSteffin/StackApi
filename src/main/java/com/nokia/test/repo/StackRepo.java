package com.nokia.test.repo;

import com.nokia.test.model.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StackRepo extends JpaRepository<Stack, Long> {

    Optional<Stack> findTopByOrderByIdDesc();
}