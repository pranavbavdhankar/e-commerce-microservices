package com.example.product_service.model;

import com.example.product_service.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {

    @Id
    String productId;

    private String name;
    private String description;
    private double price;
    @ElementCollection
    private Map<String, String> specification;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductImages> imageUrls;

    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Product(ProductDto newProduct) {
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.price = newProduct.getPrice();
        this.specification = newProduct.getSpecification();
    }
}
