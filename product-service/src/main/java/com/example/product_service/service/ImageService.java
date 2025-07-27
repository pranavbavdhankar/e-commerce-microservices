package com.example.product_service.service;

import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.model.Images;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final CloudinaryService cloudinaryService;

    private final ImageRepository imageRepository;

    public Images saveImage(MultipartFile file, Product product){

        Images image = new Images();
        image.setImageId(UUID.randomUUID().toString());
        String url = cloudinaryService.uploadFile(file);
        image.setUrl(url);
        image.setProduct(product);
        return imageRepository.save(image);

    }

    public List<Images> saveImages(List<MultipartFile> files, Product product){

        if(files.isEmpty()) return new ArrayList<>();
        List<Images> res = new ArrayList<>();
        for(MultipartFile file : files){
            Images image = saveImage(file, product);
            res.add(image);
        }
        return res;

    }

    public Images getImageById(String imageId){
        return imageRepository.findById(imageId)
                .orElseThrow(()-> new ResourceNotFoundException("Image not found"));
    }

    public Images getImageByImageIdAndProductId(String imageId, Product product){

        return imageRepository.findByImageIdAndProduct(imageId, product)
                .orElseThrow(()-> new ResourceNotFoundException("Image not found"));

    }

    public void updateImage(Product product, String imageId, MultipartFile file){

        Images image = this.getImageByImageIdAndProductId(imageId, product);
        cloudinaryService.deleteFile(image.getUrl());
        String url = cloudinaryService.uploadFile(file);
        image.setUrl(url);
        imageRepository.save(image);

    }

    public void deleteImage(Product product, String imageId){

        Images image = this.getImageByImageIdAndProductId(imageId, product);
        if(image != null){
            cloudinaryService.deleteFile(image.getUrl());
            product.getImageUrls().removeIf(img -> img.getImageId().equals(imageId));
            image.setProduct(null);
            imageRepository.delete(image);
        }

    }

    public void deleteImages(Product product, List<Images> imagesList){

        if(imagesList == null) return;
        for(Images image : imagesList){
            deleteImage(product, image.getImageId());
        }

    }

    public boolean existsById(String imageId){
        return imageRepository.existsById(imageId);
    }

}
