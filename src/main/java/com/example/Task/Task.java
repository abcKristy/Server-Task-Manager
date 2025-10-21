package com.example.Task;

import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record Task(
        @Null
        Long id,
        @NotNull
        Long creatorId,
        @NotNull
        Long assignedUserId,
        @Null
        TaskStatus status,
        @Null
        LocalDateTime createDateTime,
        @NotNull
        @FutureOrPresent
        LocalDateTime deadlineDate,
        @NotNull
        TaskPriority priority,
        @Null
        LocalDateTime doneDateTime
) {
}
