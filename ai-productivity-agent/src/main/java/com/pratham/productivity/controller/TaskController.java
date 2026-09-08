package com.pratham.productivity.controller;

import com.pratham.productivity.dto.CreateTaskRequest;
import com.pratham.productivity.entity.Task;
import com.pratham.productivity.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);

        Task createdTask = taskService.createTask(task, email);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks(Authentication authentication) {

        String email = authentication.getName();

        return taskService.getAllTasks(email);
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return taskService.getTaskById(id, email);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody CreateTaskRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return taskService.updateTask(id, request, email);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        taskService.deleteTask(id, email);

        return ResponseEntity.noContent().build();
    }
}