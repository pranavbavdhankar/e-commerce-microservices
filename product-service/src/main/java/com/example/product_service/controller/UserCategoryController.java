package com.example.product_service.controller;

import com.example.product_service.model.Category;
import com.example.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class UserCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getall")
    public ResponseEntity<List<Category>> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId){
        return categoryService.getCategoryById(categoryId);
    }

}
