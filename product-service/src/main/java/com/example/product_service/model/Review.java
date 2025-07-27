package com.example.product_service.model;

import com.example.product_service.dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    private String reviewId;
    private String comment;
    private int rating;
    private String userId;

    private LocalDateTime createAt;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "review", orphanRemoval = true)
    List<ReviewImage> images;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_product_id")
    Product product;
    public Review(ReviewDto reviewDto){
        this.comment = reviewDto.getComment();
        this.rating = reviewDto.getRating();
        this.userId = reviewDto.getUserId();
    }

}
