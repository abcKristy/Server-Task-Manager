package com.example.Task.errors;

import com.example.Task.controller.TaskController;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e){
        log.error("handle exception", e);
        var errorDto = new ErrorResponseDto(
                "Internal server error",
                e.getMessage(),
                LocalDate.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(EntityNotFoundException e){
        log.error("handle EntityNotFoundException", e);
        var errorDto = new ErrorResponseDto(
                "Entity not found",
                e.getMessage(),
                LocalDate.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler(exception = {IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e){
        log.error("handle handleIllegalArgumentException", e);
        var errorDto = new ErrorResponseDto(
                "Illegal Argument Exception, check your params in body",
                e.getMessage(),
                LocalDate.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }
}
