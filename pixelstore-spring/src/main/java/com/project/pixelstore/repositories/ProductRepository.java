package com.project.pixelstore.repositories;

import com.project.pixelstore.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' " +
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchProduct(@Param("keyword") String keyword,
                                @Param("categoryId") Long categoryId,
                                Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> getProductsByIds(@Param("ids") List<String> ids);
}
