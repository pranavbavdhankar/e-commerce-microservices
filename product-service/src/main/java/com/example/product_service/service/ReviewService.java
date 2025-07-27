package com.example.product_service.service;

import com.example.product_service.dto.ResponseDto;
import com.example.product_service.dto.ReviewDto;
import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.model.Review;
import com.example.product_service.model.ReviewImage;
import com.example.product_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewImageService reviewImageService;
    private final ProductService productService;

    public ResponseEntity<ResponseDto> addReview(ReviewDto reviewDto, List<MultipartFile> files) {

        Product product = productService.getProductById(reviewDto.getProductId()).getBody();

        Review review = new Review(reviewDto);
        review.setReviewId(UUID.randomUUID().toString());
        review.setProduct(product);
        review.setCreateAt(LocalDateTime.now());
        review = reviewRepository.save(review);
        List<ReviewImage> images = reviewImageService.addImages(review, files);
        review.setImages(images);
        reviewRepository.save(review);
        return ResponseEntity.ok(
                new ResponseDto("Review Added Sucessfully", 200, LocalDateTime.now())
        );

    }

    public ResponseEntity<ResponseDto> deleteReview(String reviewId, String productId, String userId) {

        Review review = getReviewByReviewIdAndProductIdAndUserId(reviewId, productId, userId);
        reviewImageService.deleteImages(review, review.getImages());
        reviewRepository.delete(review);

        Product product = productService.getProductById(productId).getBody();
        if(product.getReviews()!=null)
            product.getReviews().remove(review);
        productService.updateProduct(product);
        return ResponseEntity.ok(
                new ResponseDto("Review Deleted Sucessfully", 200, LocalDateTime.now())
        );

    }

    public Review getReviewByReviewIdAndProductIdAndUserId(String reviewId, String productId, String userId){

        Product product = productService.getProductById(productId).getBody();
        return reviewRepository.findReviewByReviewIdAndProductAndUserId(reviewId, product, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not found"));


    }

}