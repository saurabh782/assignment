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
    private String status; //Assigned, Unassigned
    private String deadlineStatus; //SPILLOVER, DEPLOYED, READY_FOR_DEPLOYMENT
    private String type; //Task,Subtask,Story
}
