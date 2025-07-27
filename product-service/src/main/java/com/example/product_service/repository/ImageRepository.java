package com.example.product_service.repository;

import com.example.product_service.model.Images;
import com.example.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Images, String> {

    @Query("select i from Images i where i.imageId = ?1 and i.product = ?2")
//    Optional<Images> getImagesByImageIdAndProduct(String imageId, Product product);

    Optional<Images> findByImageIdAndProduct(String imageId, Product product);
}
