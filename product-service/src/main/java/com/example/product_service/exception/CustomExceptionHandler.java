package com.example.product_service.exception;

import com.example.product_service.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto> resourceNotFoundException(ResourceNotFoundException exception){
        return ResponseEntity.status(404).body(new ResponseDto(exception.getMessage(), 404, LocalDateTime.now()));
    }
}
