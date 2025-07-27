package com.example.product_service.dto;

import com.example.product_service.model.Category;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class ProductDto {

    private String name;
    private String description;
    private double price;
    private Map<String, String> specification;
    private List<String> categories;

}
