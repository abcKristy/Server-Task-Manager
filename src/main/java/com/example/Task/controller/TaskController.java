package com.example.Task.controller;

import com.example.Task.Task;
import com.example.Task.TaskStatus;
import com.example.Task.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tasks")
@RestController
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }





    @GetMapping("/get/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long id
    ){
        log.info("getTaskByIdController done with id {}",id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Task>> getAllTasks(){
        log.info("getAllTasksController done");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getAllTasks());
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(
            @RequestBody @Valid Task taskToCreate
    ){
        log.info("createTaskController done with userId = {}", taskToCreate.creatorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskToCreate));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") Long id,
            @RequestBody @Valid Task taskToUpdate
    ){
        log.info("updateTaskController done with id ={}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateTask(id,taskToUpdate));
    }

    @PutMapping("update/{id}/status/{status}")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable("id") Long id,
            @PathVariable("status")TaskStatus status
            ){
        log.info("updateTaskController done with id ={}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateTaskStatus(id,status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Task> deleteTask(
            @PathVariable("id") Long id
    ){
        log.info("deleteTaskController done with id = {}", id);
        taskService.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
