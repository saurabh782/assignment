package com.example.demo.controller;

import com.example.demo.Entity.Task;
import com.example.demo.exception.CustomException;
import com.example.demo.response.Status;
import com.example.demo.response.TaskResponse;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService service;

    @PostMapping("/")
    TaskResponse create(@RequestBody Task task) {
        TaskResponse response = new TaskResponse();
        try {
            Task createdTask = service.create(task);
            response.setTasks(Collections.singletonList(createdTask));
            response.setStatus(new Status(1, "Task created successfully", "SUCCESS"));
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(1, "Task creation failed", "ERROR"));
            return response;
        }
    }

    @PutMapping("/{taskId}")
    TaskResponse update(@RequestBody Task task, @PathVariable Long taskId) {
        TaskResponse response = new TaskResponse();
        try {
            Task updatedTask = service.update(task, taskId);
            response.setTasks(Collections.singletonList(updatedTask));
            response.setStatus(new Status(1, "Task updation successful", "SUCCESS"));
            return response;
        } catch (CustomException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            response.setStatus(e.getStatus());
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(4, "Task updation failed", "ERROR"));
            return response;
        }
    }

    @PutMapping("/moveTask/{taskId}")
    TaskResponse moveTask(@RequestBody Task task, @PathVariable Long taskId) {
        TaskResponse response = new TaskResponse();
        try {
            Task updatedTask = service.move(task, taskId);
            response.setTasks(Collections.singletonList(updatedTask));
            response.setStatus(new Status(1, "Task moved successful", "SUCCESS"));
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(4, "Task could not be moved", "ERROR"));
            return response;
        }
    }

    @DeleteMapping("/{taskId}")
    TaskResponse delete(@PathVariable Long taskId) {
        TaskResponse response = new TaskResponse();
        try {
            service.delete(taskId);
            response.setStatus(new Status(1, "Task deletion successful", "SUCCESS"));
            return response;
        } catch (CustomException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            response.setStatus(e.getStatus());
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(4, "Task deletion failed", "ERROR"));
            return response;
        }
    }

    @GetMapping("/{userId}")
    TaskResponse getTasksForUser(@PathVariable Long userId) {
        TaskResponse response = new TaskResponse();
        try {
            List<Task> taskList = service.getTasksForUser(userId);
            response.setTasks(taskList);
            response.setStatus(new Status(1, "User tasks retrieval successful", "SUCCESS"));
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(new Status(4, "User tasks retrieval failed", "ERROR"));
            return response;
        }
    }
}
