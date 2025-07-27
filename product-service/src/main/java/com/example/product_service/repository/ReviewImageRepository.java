package com.example.product_service.repository;

import com.example.product_service.model.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, String> {
    
}
