package com.example.Task.service;

import com.example.Task.TaskEntity;
import com.example.Task.controller.TaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final HashMap<Long, TaskEntity> tasks;

    public TaskService(HashMap<Long, TaskEntity> tasks) {
        this.tasks = tasks;
        tasks.put(1L, new TaskEntity());
        tasks.put(2L, new TaskEntity());
        tasks.put(3L, new TaskEntity());
        tasks.put(4L, new TaskEntity());
    }

    public TaskEntity getTaskById(Long id){
        if(!tasks.containsKey(id))
            throw new NoSuchElementException("task with id "+ id + " not exist");
        log.info("getTaskById done with id {}",id);
        return tasks.get(id);
    }

    public List<TaskEntity> getAllTasks(){
        log.info("getAllTasks done");
        return new ArrayList<>(tasks.values());
    }
}
