package com.example.product_service.dto;

import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {

    private String comment;
    private int rating;
    private String productId;
    private String userId;
    
}
