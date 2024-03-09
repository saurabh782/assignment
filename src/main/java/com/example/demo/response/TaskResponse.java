package com.example.demo.response;

import com.example.demo.Entity.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskResponse {
    private Status status;
    private List<Task> tasks;
}
