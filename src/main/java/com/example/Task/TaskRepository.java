package com.example.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    long countByAssignedUserIdAndStatus(Long assignedUserId, TaskStatus status);

}
