package com.example.Task.service;

import com.example.Task.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final HashMap<Long, Task> tasksMap;
    private final TaskMapper mapper;
    private final AtomicLong idCounter;

    public TaskService(TaskMapper mapper) {
        this.tasksMap = new HashMap<>();
        this.mapper = mapper;
        this.idCounter = new AtomicLong();
    }

    public Task getTaskById(Long id){
        log.info("getTaskByIdServer done with id {}",id);

        if(!tasksMap.containsKey(id))
            throw new NoSuchElementException("task with id "+ id + " not exist");

        return tasksMap.get(id);
    }

    public List<Task> getAllTasks(){
        log.info("getAllTasksServer done");
        return new ArrayList<>(tasksMap.values());
    }

    public Task createTask(Task taskToCreate) {
        log.info("createTaskServer done with id {}",idCounter.get());

        if(taskToCreate.status()!=null)
            throw new IllegalArgumentException("status should be empty");
        if(taskToCreate.deadlineDate().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("deadline must be not before you create time");

        var entityToSave = mapper.toEntity(taskToCreate);
        entityToSave.setId(idCounter.getAndIncrement());
        entityToSave.setCreateDateTime(LocalDateTime.now());
        entityToSave.setStatus(TaskStatus.CREATED);

        var savedTask = mapper.toTask(entityToSave);
        tasksMap.put(savedTask.id(), savedTask);

        return savedTask;
    }


    public Task updateTask(Long id, Task taskToUpdate) {
        log.info("updateTaskServer done with id {}",id);

        if(!tasksMap.containsKey(id))
            throw new NoSuchElementException("task with id "+ id + " not exist");

        Task existingTask = tasksMap.get(id);

        if (taskToUpdate.deadlineDate().isBefore(existingTask.createDateTime())) {
            throw new IllegalArgumentException("Deadline must not be before create date");
        }

        var updateEntity = mapper.toEntity(existingTask);
        updateEntity.setCreatorId(taskToUpdate.creatorId());
        updateEntity.setAssignedUserId(taskToUpdate.assignedUserId());
        updateEntity.setStatus(taskToUpdate.status());
        updateEntity.setDeadlineDate(taskToUpdate.deadlineDate());
        updateEntity.setPriority(taskToUpdate.priority());

        var updatedTask = mapper.toTask(updateEntity);
        tasksMap.put(updatedTask.id(),updatedTask);

        return updatedTask;
    }

    public void deleteTask(Long id) {
        log.info("deleteTaskServer done with id {}",id);

        if(!tasksMap.containsKey(id))
            throw new NoSuchElementException("task with id "+ id + " not exist");

        tasksMap.remove(id);
    }
}
