package com.example.product_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReviewImage {

    @Id
    private String reviewImageId;
    private String imageUrl;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "review_review_id")
    Review review;
}
