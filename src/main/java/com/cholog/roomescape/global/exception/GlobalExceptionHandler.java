package com.cholog.roomescape.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        logger.warn(e.getMessage(), e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(BadRequestException e){
        logger.warn(e.getMessage(), e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleRuntimeException(NotFoundException e){
        logger.warn(e.getMessage(), e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Void> handleConflictException(ConflictException e){
        logger.warn(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
