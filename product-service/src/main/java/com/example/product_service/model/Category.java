package com.example.product_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Category {
    @Id
    private String categoryId;
    private String name;

    @JsonBackReference
    @ManyToMany( mappedBy = "categories")
    private Set<Product> products;
}
