package com.example.Task.filter;

import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;

public record TaskFilter(
        Long creatorId,
        Long assignedUserId,
        TaskStatus status,
        TaskPriority priority,
        Integer pageSize, Integer pageNumber) {
}
