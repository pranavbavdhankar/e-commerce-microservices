package com.example.product_service.controller;


import com.example.product_service.dto.ResponseDto;
import com.example.product_service.model.Category;
import com.example.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/admin")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable String categoryId, @RequestBody Category category){
        return categoryService.updateCategory(categoryId, category);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable String categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
