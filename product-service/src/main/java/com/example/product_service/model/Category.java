package com.example.product_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {
    @Id
    private String categoryId;
    private String name;

    @JsonIgnore
    @JsonBackReference(value="product-category")
    @ManyToMany( mappedBy = "categories")
    private List<Product> products;
}
