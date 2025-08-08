package com.example.product_service.controller;

import com.example.product_service.model.Product;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class UserProductController {

    private final ProductService productService;

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

    @GetMapping("get-by/user/{userEmail}")
    public ResponseEntity<Page<Product>> getAllProductByAdmin(@PathVariable String userEmail, Pageable pageable){
        return productService.getAllProductsByAdmin(userEmail, pageable);
    }

}
