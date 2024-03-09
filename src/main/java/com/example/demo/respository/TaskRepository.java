package com.example.demo.respository;

import com.example.demo.Entity.Task;
import com.example.demo.exception.CustomException;
import com.example.demo.response.Status;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TaskRepository {
    private List<Task> taskList = new ArrayList<>();

    public Task create(Task task) {
        Long maxTaskId = 0L;
        for(Task existingTask : this.taskList) {
            maxTaskId = Math.max(existingTask.getTaskId(), maxTaskId);
        }

        task.setTaskId(maxTaskId);
        taskList.add(task);
        return task;
    }

    public Task update(Task task, Long taskId) throws CustomException {
        for(Task existingTask : this.taskList) {
            if(existingTask.getTaskId().equals(taskId)) {
                if(StringUtils.isNotEmpty(task.getDescription())) {
                    existingTask.setDescription(task.getDescription());
                }

                if(StringUtils.isNotEmpty(task.getStatus())) {
                    existingTask.setStatus(task.getStatus());
                }

                if(Objects.nonNull(task.getDeadline())) {
                    existingTask.setDeadline(task.getDeadline());
                }
                return existingTask;
            }
        }
        throw new CustomException(new Status(1, "Invalid task id ", "ERROR"));
    }

    public void delete(Long taskId) throws CustomException {
        int indexToRemove = -1;
        for(int index = 0; index < taskList.size() ; index++) {
            if(taskList.get(index).getTaskId().equals(taskId)) {
                indexToRemove = index;
                break;
            }
        }

        if(indexToRemove == -1) {
            throw new CustomException(new Status(1, "Invalid task id ", "ERROR"));
        }

        taskList.remove(indexToRemove);
    }

    public List<Task> getTasksForUser(Long userId) {
        List<Task> userTasks = new ArrayList<>();
        for(Task existingTask : this.taskList) {
            if(existingTask.getAssigneeId().equals(userId)) {
                userTasks.add(existingTask);
            }
        }

        return userTasks;
    }
}
