package com.example.Task;


import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Table(name = "TaskManager")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    public Long id;
    @Column(name ="creator_ud")
    public Long creatorId;
    @Column(name ="assigned_user_id")
    public Long assignedUserId;
    @Enumerated(EnumType.STRING)
    @Column(name ="status")
    public TaskStatus status;
    @Column(name ="create_date_time")
    public LocalDateTime createDateTime;
    @Column(name ="deadline_date")
    public LocalDateTime deadlineDate;
    @Enumerated(EnumType.STRING)
    @Column(name ="priority")
    public TaskPriority priority;

    public TaskEntity() {
    }

    public TaskEntity(Long id,
                      Long creatorId,
                      Long assignedUserId,
                      TaskStatus status,
                      LocalDateTime createDateTime,
                      LocalDateTime deadlineDate,
                      TaskPriority priority) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDateTime = createDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
