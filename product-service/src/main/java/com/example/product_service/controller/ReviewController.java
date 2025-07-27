package com.example.product_service.controller;

import com.example.product_service.dto.ResponseDto;
import com.example.product_service.dto.ReviewDto;
import com.example.product_service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("add")
    public ResponseEntity<ResponseDto> addReview(@RequestPart("review") ReviewDto reviewDto, @RequestPart(value = "files", required = false) ArrayList<MultipartFile> files){
        return reviewService.addReview(reviewDto, files);
    }

    @PostMapping("/delete/{reviewId}/product/{productId}/user/{userId}")
    public ResponseEntity<ResponseDto> deleteReview(@PathVariable String reviewId, @PathVariable String productId, @PathVariable String userId){
        return reviewService.deleteReview(reviewId, productId, userId);
    }

}
