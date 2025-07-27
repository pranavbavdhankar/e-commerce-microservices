package com.example.product_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    String reviewId;
    String comment;
    int rating;
    LocalDateTime createAt;

    String productId;
    String userId;

}
