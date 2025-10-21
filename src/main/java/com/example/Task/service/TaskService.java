package com.example.Task.service;

import com.example.Task.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Task getTaskById(Long id){
        log.info("getTaskByIdServer done with id {}",id);

        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found task by id = "+id
                ));

        return mapper.toTask(taskEntity);
    }

    public List<Task> getAllTasks(){
        log.info("getAllTasksServer done");
        List<TaskEntity> allEntities = repository.findAll();
        return allEntities.stream().map
                (mapper::toTask).toList();
    }

    public Task createTask(Task taskToCreate) {
        log.info("createTaskServer done");

        if(taskToCreate.status()!=null)
            throw new IllegalArgumentException("status should be empty");
        if(taskToCreate.deadlineDate().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("deadline must be not before you create time");

        var entityToSave = mapper.toEntity(taskToCreate);
        entityToSave.setCreateDateTime(LocalDateTime.now());
        entityToSave.setStatus(TaskStatus.CREATED);

        repository.save(entityToSave);

        return mapper.toTask(entityToSave);
    }


    public Task updateTask(Long id, Task taskToUpdate) {
        log.info("updateTaskServer done with id {}",id);

        TaskEntity updateEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found task by id = "+id
                ));
        if(taskToUpdate.status()!=null)
            throw new IllegalArgumentException("status should be  you cannot change it without manager");
        if(updateEntity.getStatus().equals(TaskStatus.DONE))
            throw new IllegalArgumentException("Task has done yes you cannot edit it");
        if (taskToUpdate.deadlineDate().isBefore(updateEntity.getCreateDateTime())) {
            throw new IllegalArgumentException("Deadline must not be before create date");
        }

        updateEntity.setCreatorId(taskToUpdate.creatorId());
        updateEntity.setAssignedUserId(taskToUpdate.assignedUserId());
        updateEntity.setDeadlineDate(taskToUpdate.deadlineDate());
        updateEntity.setPriority(taskToUpdate.priority());
        repository.save(updateEntity);

        return mapper.toTask(updateEntity);
    }

    public void deleteTask(Long id) {
        log.info("deleteTaskServer done with id {}",id);

        TaskEntity updateEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found task by id = "+id
                ));

        repository.deleteById(id);
    }

    public Task updateTaskStatus(Long id, TaskStatus status) {
        log.info("updateTaskStatusServer done with id {}",id);

        TaskEntity updateEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found reservation by id = "+id
                ));

        updateEntity.setStatus(status);
        repository.save(updateEntity);
        return mapper.toTask(updateEntity);
    }
}
