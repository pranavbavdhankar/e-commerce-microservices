package com.example.product_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Images {
    @Id
    String imageId;
    private String url;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_product_id")
    private Product product;
}
