package com.example.demo.repository;

import com.example.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Custom query methods can be added here if needed
}

