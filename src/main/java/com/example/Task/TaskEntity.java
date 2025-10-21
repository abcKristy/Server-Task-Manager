package com.example.Task;


import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TaskManager")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    public Long id;
    @Column(name ="creator_id")
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
    @Column(name ="done_date_time")
    public LocalDateTime doneDateTime;

    public TaskEntity() {
    }

    public TaskEntity(Long id,
                      Long creatorId,
                      Long assignedUserId,
                      TaskStatus status,
                      LocalDateTime createDateTime,
                      LocalDateTime deadlineDate,
                      TaskPriority priority,
                      LocalDateTime doneDateTime) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDateTime = createDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
        this.doneDateTime = doneDateTime;
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
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
