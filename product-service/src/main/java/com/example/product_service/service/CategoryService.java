package com.example.product_service.service;

import com.example.product_service.dto.ResponseDto;
import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.model.Category;
import com.example.product_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<ResponseDto> addCategory(Category category) {
        category.setCategoryId(UUID.randomUUID().toString());
        Category newCategory =  categoryRepository.save(category);
        return ResponseEntity.ok(
                new ResponseDto("Category added Successfully", 201, LocalDateTime.now())
        );
    }

    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<Category> getCategoryById(String categoryId) {

        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow( ()-> new ResourceNotFoundException("Category Not found"));
        return ResponseEntity.ok(category);

    }

    public ResponseEntity<List<Category>> getCategoriesByIds(List<String> categoryIds) {
        if(categoryIds == null) return ResponseEntity.ok(null);
        List<Category> categories = new ArrayList<>();
        for(String id : categoryIds){
            Category category = this.getCategoryById(id).getBody();
            categories.add(category);
        }
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<Category> updateCategory(String categoryId, Category category) {
        Category oldCategory = this.getCategoryById(categoryId).getBody();
        oldCategory.setName(category.getName());
        Category updatedCategory = categoryRepository.save(oldCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    public ResponseEntity<ResponseDto> deleteCategory(String categoryId) {
        Category category = this.getCategoryById(categoryId).getBody();
        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }

}
