package com.example.dto.tasks;

import com.example.models.Task;

public record TaskResponse(Long id, String content, boolean done) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getContent(), task.isDone());
    }
}
