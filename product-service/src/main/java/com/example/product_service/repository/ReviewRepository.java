package com.example.product_service.repository;

import com.example.product_service.model.Product;
import com.example.product_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    Optional<Review> findReviewByReviewIdAndProductAndUserId(String reviewId, Product productId, String userId);

}
