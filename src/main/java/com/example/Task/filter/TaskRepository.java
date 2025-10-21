package com.example.Task.filter;

import com.example.Task.entities.TaskEntity;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    long countByAssignedUserIdAndStatus(Long assignedUserId, TaskStatus status);

    @Query("""
            select t from TaskEntity t
            where(:assignedUserId is null or t.assignedUserId = :assignedUserId)
            and(:creatorId is null or t.creatorId = :creatorId)
            and(:status is null or t.status = :status)
            and(:priority is null or t.priority = :priority)
            """)
    List<TaskEntity> searchAllByFilter(
            @Param("assignedUserId") Long assignedUserId,
            @Param("creatorId") Long creatorId,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable);
}
