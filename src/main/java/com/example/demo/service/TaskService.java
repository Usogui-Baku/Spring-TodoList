package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(@NotNull(message = "Task cannot be null") Task task) throws RuntimeException {
        if (taskRepository.findByTitle(task.getTitle()).isPresent()) {
            throw new RuntimeException("Task already exists");
        }
        return taskRepository.save(task);
    }

    public Task getTaskById(@NotNull(message = "Id cannot be null") Long id) throws RuntimeException {
        return taskRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Task not found")
                );
    }

    public Task getTaskByTitle(
            @NotNull(message = "Title cannot be null")
            @NotBlank(message = "Title cannot be blank")
            String title
    ) throws RuntimeException {
        return taskRepository.findByTitle(title)
                .orElseThrow(
                        () -> new RuntimeException("Task not found")
                );
    }

    public List<Task> getAllTasks() {
        if (taskRepository.findAll().isEmpty()) {
            return List.of();
        }
        return taskRepository.findAll();
    }

    public Task updateTask(@NotNull(message = "Task cannot be null") Task task) throws RuntimeException {
        Optional<Task> taskByTitle = taskRepository.findByTitle(task.getTitle());
        if (taskByTitle.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        Task taskToUpdate = taskByTitle.get();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setCompleted(task.isCompleted());
        taskToUpdate.setDueDate(task.getDueDate());
        return taskRepository.save(taskToUpdate);
    }

    public void deleteTask(@NotNull(message = "Task cannot be null") Task task) throws RuntimeException {
        Optional<Task> taskByTitle = taskRepository.findByTitle(task.getTitle());
        if (taskByTitle.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.delete(task);
    }

    public List<Task> getPendingTasks() {
        List<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            return List.of();
        }
        return allTasks.stream()
                .filter(task -> !task.isCompleted())
                .toList();
    }

    public List<Task> getCompletedTasks() {
        List<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            return List.of();
        }
        return allTasks.stream()
                .filter(Task::isCompleted)
                .toList();
    }

    public List<Task> getTodayTasks() {
        List<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            return List.of();
        }
        return allTasks.stream()
                .filter(
                        task -> !task.isCompleted()
                )
                .filter(
                        task -> task.getDueDate()
                                .isEqual(LocalDate.now())
                )
                .toList();
    }
}

