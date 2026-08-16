package com.hello.hotelservice.exceptionhandler;

import com.hello.hotelservice.exception.ResourceNotFoundException;
import com.hello.hotelservice.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        log.error("Resource not found exception thrown: {}", e.getMessage());
        return new ResponseEntity<>(ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .build(), HttpStatus.NOT_FOUND);
    }
}
