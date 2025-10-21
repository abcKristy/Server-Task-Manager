package com.example.Task.controller;

import com.example.Task.Task;
import com.example.Task.TaskFilter;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
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
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(name = "creator_id", required = false) Long creatorId,
            @RequestParam(name = "assigned_user_id", required = false) Long assignedUserId,
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "priority", required = false) TaskPriority priority,
            @RequestParam(name = "priority", required = false) Integer pageSize,
            @RequestParam(name = "priority", required = false) Integer pageNumber
    ){
        log.info("getAllTasksController done");

        var filter = new TaskFilter(
                creatorId,
                assignedUserId,
                status,
                priority,
                pageSize,
                pageNumber
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.searchAllByFilter(filter));
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

    @PostMapping("update/{id}/start")
    public ResponseEntity<Task> updateTaskStatusInProgress(
            @PathVariable("id") Long id
            ){
        log.info("updateTaskStartController done with id ={}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateTaskStatusStart(id));
    }

    @PostMapping("update/{id}/finish")
    public ResponseEntity<Task> updateTaskStatusDone(
            @PathVariable("id") Long id
    ){
        log.info("updateTaskFinishController done with id ={}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateTaskStatusDone(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id") Long id
    ){
        log.info("deleteTaskController done with id = {}", id);
        taskService.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
