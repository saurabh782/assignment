package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Task {
    private Long taskId;
    private String title;
    private String description;
    private Long assigneeId;
    private Long parentTaskId;
    private Long storyId;
    private Date deadline;
    private Long creatorId;
    private String status;
    private String type;
}
