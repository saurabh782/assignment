package com.example.demo.service;

import com.example.demo.Entity.Task;
import com.example.demo.exception.CustomException;
import com.example.demo.respository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository repository;

    public Task create(Task task) {
        Task createdTask = repository.create(task);
        return createdTask;
    }

    public Task update(Task task, Long taskId) throws CustomException {
        Task updatedTask = repository.update(task, taskId);
        return updatedTask;
    }

    public void delete(Long taskId) throws CustomException {
        repository.delete(taskId);
    }

    public List<Task> getTasksForUser(Long userId) {
        List<Task> tasks = repository.getTasksForUser(userId);
        return tasks;
    }

    public Task move(Task task, Long taskId) throws CustomException {
        Task updatedTask = repository.update(task, taskId);
        return updatedTask;
    }
}
