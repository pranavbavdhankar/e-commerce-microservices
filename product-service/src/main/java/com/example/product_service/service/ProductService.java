package com.example.product_service.service;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.ResponseDto;
import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.model.Images;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ImageService imageService;

    private final Logger logger  = Logger.getLogger(this.getClass().toString());

    public ResponseEntity<ResponseDto> addProduct(ProductDto newProduct, ArrayList<MultipartFile> files) {

        Product product = new Product(newProduct);
        try{
            product.setProductId(UUID.randomUUID().toString());
            product.setCreateAt(LocalDateTime.now());
            product.setUpdateAt(LocalDateTime.now());
            product.setCategories(
                    categoryService.getCategoriesByIds(newProduct.getCategories()).getBody()
            );
            Product savedProduct = productRepository.save(product);
            List<Images> images = imageService.saveImages(files, savedProduct);
            product.setImageUrls(images);
            productRepository.save(product);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(new ResponseDto("Product added successfully", 201, LocalDateTime.now()), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseDto> addProductImage(String productId, MultipartFile file) {

        Product product = getProductById(productId).getBody();
        Images image = imageService.saveImage(file, product);
        if(product.getImageUrls() != null){
            product.getImageUrls().add(image);
        }else{
            List<Images> list = new ArrayList<>();
            list.add(image);
            product.setImageUrls(list);
        }
        productRepository.save(product);
        return ResponseEntity.ok(
                new ResponseDto("Image Added Successfully", 200, LocalDateTime.now())
        );

    }

    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }

    public ResponseEntity<Product> getProductById(String productId) {
        return ResponseEntity.ok(productRepository.getProductByProductId(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not found")));
    }

    public ResponseEntity<Page<Product>> getProductsByCategory(String categoryName, Pageable pageable) {

        Page<Product> products = productRepository.getProductByCategoryName(categoryName, pageable);
        return ResponseEntity.ok(products);

    }

    public ResponseEntity<Product> updateProduct(String productId, ProductDto product) {

        Product existingProduct = this.getProductById(productId).getBody();
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSpecification(product.getSpecification());
        existingProduct.setCategories(
                categoryService.getCategoriesByIds(product.getCategories()).getBody()
        );
        existingProduct.setUpdateAt(LocalDateTime.now());
        Product updatedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(updatedProduct);

    }

    public ResponseEntity<ResponseDto> updateProductImage(String productId, String imageId, MultipartFile file) {

        Product product = this.getProductById(productId).getBody();
        imageService.updateImage(product ,imageId, file);
        productRepository.save(product);
        return ResponseEntity.ok(
                new ResponseDto("Image Updated Successfully", 200, LocalDateTime.now())
        );

    }

    public ResponseEntity<ResponseDto> deleteProductById(String productId) {

            Product product = this.getProductById(productId).getBody();
            imageService.deleteImages(product, product.getImageUrls());
            productRepository.delete(product);
            return new ResponseEntity<>(
                    new ResponseDto("Product Deleted Successfully", 404, LocalDateTime.now()), HttpStatus.NO_CONTENT
            );

    }

    public ResponseEntity<ResponseDto> deleteProductImage(String productId, String imageId) {

        Product product = this.getProductById(productId).getBody();
        imageService.deleteImage(product, imageId);
        if(product.getImageUrls() != null && product.getImageUrls().size() == 1){
            product.setImageUrls(null);
        }
        productRepository.save(product);
        return ResponseEntity.ok(
                new ResponseDto("Image Deleted Successfully", 200, LocalDateTime.now())
        );

    }
}
