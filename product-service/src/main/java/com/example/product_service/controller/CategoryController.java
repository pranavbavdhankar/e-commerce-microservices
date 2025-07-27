package com.example.product_service.controller;


import com.example.product_service.dto.ResponseDto;
import com.example.product_service.model.Category;
import com.example.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Category>> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable String categoryId, @RequestBody Category category){
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable String categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
