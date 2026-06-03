package com.portfolio.tasks.service;

import com.portfolio.tasks.dto.TaskDto;
import com.portfolio.tasks.model.Task;
import com.portfolio.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public List<TaskDto.Response> findAll() {
        return repository.findAll().stream()
                .map(TaskDto.Response::from)
                .toList();
    }

    public List<TaskDto.Response> findByStatus(Task.Status status) {
        return repository.findByStatus(status).stream()
                .map(TaskDto.Response::from)
                .toList();
    }

    public TaskDto.Response findById(Long id) {
        return repository.findById(id)
                .map(TaskDto.Response::from)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada: " + id));
    }

    @Transactional
    public TaskDto.Response create(TaskDto.Request request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        return TaskDto.Response.from(repository.save(task));
    }

    @Transactional
    public TaskDto.Response update(Long id, TaskDto.Request request) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada: " + id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        return TaskDto.Response.from(repository.save(task));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Tarea no encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
