package com.example.product_service.controller;


import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.ResponseDto;
import com.example.product_service.model.Product;
import com.example.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/product/admin")
@AllArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add")
    public ResponseEntity<ResponseDto> addProduct(
            @RequestPart("product") ProductDto newProduct, @RequestPart("files") ArrayList<MultipartFile> files){
        return productService.addProduct(newProduct, files);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add/image/{productId}")
    public ResponseEntity<ResponseDto> addProductImage(@PathVariable String productId, MultipartFile file){
        return productService.addProductImage(productId, file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody ProductDto product){
        return productService.updateProduct(productId, product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("update/{productId}/image/{imageId}")
    public ResponseEntity<ResponseDto> updateProductImage(@PathVariable String productId, @PathVariable String imageId, MultipartFile file){
        return productService.updateProductImage(productId, imageId, file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("delete/{productId}")
    public ResponseEntity<ResponseDto> deleteProductById(@PathVariable String productId){
        return productService.deleteProductById(productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("delete/{productId}/image/{imageId}")
    public ResponseEntity<ResponseDto> deleteProductImage(@PathVariable String productId, @PathVariable String imageId){
        return productService.deleteProductImage(productId, imageId);
    }

}
