package com.example.Task.service;

import com.example.Task.entities.Task;
import com.example.Task.entities.TaskEntity;
import com.example.Task.enums.TaskMapper;
import com.example.Task.enums.TaskStatus;
import com.example.Task.filter.TaskFilter;
import com.example.Task.filter.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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
        if(updateEntity.getStatus().equals(TaskStatus.DONE_IN_TIME) || updateEntity.getStatus().equals(TaskStatus.DONE_AFTER_DEADLINE))
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

        if (repository.findById(id).isEmpty())
            throw new NoSuchElementException("Not found reservation by id");

        repository.deleteById(id);
    }

    public Task updateTaskStatusDone(Long id) {
        log.info("updateTaskStatusServer done with id {}",id);

        TaskEntity updateEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found reservation by id = "+id
                ));
        if((updateEntity.getStatus()==TaskStatus.DONE_AFTER_DEADLINE)||(updateEntity.getStatus()==TaskStatus.DONE_IN_TIME))
            throw new IllegalArgumentException("Task has already finished");

        updateEntity.setDoneDateTime(LocalDateTime.now());
        if(updateEntity.getDeadlineDate().isAfter(updateEntity.getDoneDateTime()))
            updateEntity.setStatus(TaskStatus.DONE_IN_TIME);
        else updateEntity.setStatus(TaskStatus.DONE_AFTER_DEADLINE);
        repository.save(updateEntity);
        return mapper.toTask(updateEntity);
    }

    public Task updateTaskStatusStart(Long id) {
        log.info("startTaskServer done with id {}", id);

        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found task by id = " + id));

        if (taskEntity.getStatus() != TaskStatus.CREATED) {
            throw new IllegalArgumentException("Task has already started or even finished");
        }

        long activeTasksCount = repository.countByAssignedUserIdAndStatus(
                taskEntity.getAssignedUserId(), TaskStatus.IN_PROGRESS);

        if (activeTasksCount >= 5) {
            throw new IllegalArgumentException(
                    "User " + taskEntity.getAssignedUserId() +
                            " already has " + activeTasksCount +
                            " active tasks. Maximum allowed is 5."
            );
        }

        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        var savedEntity = repository.save(taskEntity);
        return mapper.toTask(savedEntity);
    }

    public List<Task> searchAllByFilter(TaskFilter filter) {
        int pageSize = filter.pageSize() != null
                ? filter.pageSize() : 10;
        int pageNumber = filter.pageNumber() != null
                ? filter.pageNumber() : 0;
        var pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        List<TaskEntity> allEntities = repository.searchAllByFilter(
                filter.assignedUserId(),
                filter.creatorId(),
                filter.status(),
                filter.priority(),
                pageable
        );
        return allEntities.stream().map(mapper::toTask).toList();
    }
}
