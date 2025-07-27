package com.example.product_service.repository;

import com.example.product_service.model.Product;
import com.example.product_service.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImages, String> {

    @Query("select i from ProductImages i where i.imageId = ?1 and i.product = ?2")
    Optional<ProductImages> findByImageIdAndProduct(String imageId, Product product);

}
