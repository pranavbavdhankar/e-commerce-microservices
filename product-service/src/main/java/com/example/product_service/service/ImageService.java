package com.example.product_service.service;

import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductImages;
import com.example.product_service.repository.ProductImageRepository;
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

    private final ProductImageRepository imageRepository;

    public ProductImages saveImage(MultipartFile file, Product product){

        ProductImages image = new ProductImages();
        image.setImageId(UUID.randomUUID().toString());
        String url = cloudinaryService.uploadFile(file);
        image.setUrl(url);
        image.setProduct(product);
        return imageRepository.save(image);

    }

    public List<ProductImages> saveImages(List<MultipartFile> files, Product product){

        if(files.isEmpty()) return new ArrayList<>();
        List<ProductImages> res = new ArrayList<>();
        for(MultipartFile file : files){
            ProductImages image = saveImage(file, product);
            res.add(image);
        }
        return res;

    }

    public ProductImages getImageById(String imageId){
        return imageRepository.findById(imageId)
                .orElseThrow(()-> new ResourceNotFoundException("Image not found"));
    }

    public ProductImages getImageByImageIdAndProductId(String imageId, Product product){

        return imageRepository.findByImageIdAndProduct(imageId, product)
                .orElseThrow(()-> new ResourceNotFoundException("Image not found"));

    }

    public void updateImage(Product product, String imageId, MultipartFile file){

        ProductImages image = this.getImageByImageIdAndProductId(imageId, product);
        cloudinaryService.deleteFile(image.getUrl());
        String url = cloudinaryService.uploadFile(file);
        image.setUrl(url);
        imageRepository.save(image);

    }

    public void deleteImage(Product product, String imageId){

        ProductImages image = this.getImageByImageIdAndProductId(imageId, product);
        if(image != null){
            cloudinaryService.deleteFile(image.getUrl());
            product.getImageUrls().removeIf(img -> img.getImageId().equals(imageId));
            image.setProduct(null);
            imageRepository.delete(image);
        }

    }

    public void deleteImages(Product product, List<ProductImages> imagesList){

        if(imagesList == null) return;
        ArrayList<ProductImages> copy = new ArrayList<>(imagesList);
        for(ProductImages image : copy){
            deleteImage(product, image.getImageId());
        }

    }

    public boolean existsById(String imageId){
        return imageRepository.existsById(imageId);
    }

}
