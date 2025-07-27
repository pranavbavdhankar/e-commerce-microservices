package com.example.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseDto {
    String message;
    int statusCode;
    LocalDateTime timeStamp;
}
