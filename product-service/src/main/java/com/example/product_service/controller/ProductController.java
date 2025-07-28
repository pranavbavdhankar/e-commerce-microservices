package com.example.product_service.controller;


import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.ResponseDto;
import com.example.product_service.model.Product;
import com.example.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("add")
    public ResponseEntity<ResponseDto> addProduct(
            @RequestPart("product") ProductDto newProduct, @RequestPart("files") ArrayList<MultipartFile> files){
        return productService.addProduct(newProduct, files);
    }

    @PostMapping("add/image/{productId}")
    public ResponseEntity<ResponseDto> addProductImage(@PathVariable String productId, MultipartFile file){
        return productService.addProductImage(productId, file);
    }

    @GetMapping("getall")
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("get/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId){
        return productService.getProductById(productId);
    }

    @GetMapping("get/category/{categoryName}")
    public ResponseEntity<Page<Product>> getProductsByCategory(@PathVariable String categoryName, Pageable pageable){
        return productService.getProductsByCategory(categoryName, pageable);
    }

    @GetMapping("get/search")
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam("query") String query, Pageable pageable){
        return productService.searchProducts(query, pageable);
    }
    @PostMapping("update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody ProductDto product){
        return productService.updateProduct(productId, product);
    }

    @PostMapping("update/{productId}/image/{imageId}")
    public ResponseEntity<ResponseDto> updateProductImage(@PathVariable String productId, @PathVariable String imageId, MultipartFile file){
        return productService.updateProductImage(productId, imageId, file);
    }

    @PostMapping("delete/{productId}")
    public ResponseEntity<ResponseDto> deleteProductById(@PathVariable String productId){
        return productService.deleteProductById(productId);
    }

    @PostMapping("delete/{productId}/image/{imageId}")
    public ResponseEntity<ResponseDto> deleteProductImage(@PathVariable String productId, @PathVariable String imageId){
        return productService.deleteProductImage(productId, imageId);
    }

}
