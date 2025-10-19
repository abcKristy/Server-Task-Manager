package com.example.Task.controller;

import com.example.Task.TaskEntity;
import com.example.Task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("GET/tasks/{id}")
    public ResponseEntity<TaskEntity> getTaskById(
            @PathVariable Long id
    ){
        log.info("getTaskByIdController done with id {}",id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @GetMapping("GET/tasks")
    public ResponseEntity<List<TaskEntity>> getAllTasks(){
        log.info("done getAllTasksController");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getAllTasks());
    }
}
