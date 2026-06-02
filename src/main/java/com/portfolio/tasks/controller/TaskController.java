package com.portfolio.tasks.controller;

import com.portfolio.tasks.dto.TaskDto;
import com.portfolio.tasks.model.Task;
import com.portfolio.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public List<TaskDto.Response> getAll(
            @RequestParam(required = false) Task.Status status) {
        if (status != null) {
            return service.findByStatus(status);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto.Response> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto.Response create(@Valid @RequestBody TaskDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskDto.Request request) {
        try {
            return ResponseEntity.ok(service.update(id, request));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
