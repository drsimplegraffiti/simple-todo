package com.example.simple.controller;

import com.example.simple.model.TodoDTO;
import com.example.simple.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() { // the ? means that the return type is a generic type
        List<TodoDTO> todos = todoRepository.findAll();
        if (todos.size() > 0) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todo.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable String id) {
        try {
            Optional<TodoDTO> todoOptional = todoRepository.findById(id);
            if (todoOptional.isPresent()) {
                return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody TodoDTO todo) {
        try {
            Optional<TodoDTO> todoOptional = todoRepository.findById(id);
            if (todoOptional.isPresent()) {
                TodoDTO todoToSave = todoOptional.get();

                todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
                todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
                todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
                todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
                todoRepository.save(todoToSave);
                return new ResponseEntity<>(todoToSave, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            Optional<TodoDTO> todoOptional = todoRepository.findById(id);
            if (todoOptional.isPresent()) {
                todoRepository.deleteById(id);
                return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
