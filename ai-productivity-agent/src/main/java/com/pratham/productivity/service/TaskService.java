package com.pratham.productivity.service;

import com.pratham.productivity.dto.CreateTaskRequest;
import com.pratham.productivity.entity.Task;
import com.pratham.productivity.entity.User;
import com.pratham.productivity.exception.ResourceNotFoundException;
import com.pratham.productivity.repository.TaskRepository;
import com.pratham.productivity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(CreateTaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return taskRepository.findByUserId(user.getId());
    }

    public Task getTaskById(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        if (task.getUser() == null ||
                !task.getUser().getId().equals(user.getId())) {

            throw new ResourceNotFoundException("Task not found");
        }

        return task;
    }

    public Task updateTask(
            Long id,
            CreateTaskRequest request,
            String email) {

        Task task = getTaskById(id, email);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String email) {

        Task task = getTaskById(id, email);

        taskRepository.delete(task);
    }
}
