package com.example.product_service.service;

import com.example.product_service.model.Review;
import com.example.product_service.model.ReviewImage;
import com.example.product_service.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    private final CloudinaryService cloudinaryService;
    public ReviewImage addImage(Review review, MultipartFile file){

        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setReview(review);
        reviewImage.setReviewImageId(UUID.randomUUID().toString());
        String url = cloudinaryService.uploadFile(file);
        reviewImage.setImageUrl(url);
        return reviewImageRepository.save(reviewImage);

    }

    public List<ReviewImage> addImages(Review review, List<MultipartFile> files){

        if(files == null || files.isEmpty()) return null;
        List<ReviewImage> res = new ArrayList<>();
        for(MultipartFile file : files){
            ReviewImage reviewImage = addImage(review, file);
            res.add(reviewImage);
        }
        return res;

    }

    public void deleteImage(Review review, ReviewImage image){

        cloudinaryService.deleteFile(image.getImageUrl());
        reviewImageRepository.delete(image);

    }

    public void deleteImages(Review review, List<ReviewImage> images) {

        if(images != null){
            for(ReviewImage image : images){
                deleteImage(review, image);
            }
        }

    }

}
