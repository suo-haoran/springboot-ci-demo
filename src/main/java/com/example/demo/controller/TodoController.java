package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.model.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return userRepository.findById(todo.getUser().getId())
                .map(user -> {
                    todo.setUser(user);
                    Todo savedTodo = todoRepository.save(todo);
                    return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setDescription(todoDetails.getDescription());
                    todo.setCompleted(todoDetails.getCompleted());
                    return ResponseEntity.ok().body(todoRepository.save(todo));
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
