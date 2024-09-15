package com.management.product.datasources.repository;

import com.management.product.datasources.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("SELECT p FROM ProductEntity p WHERE p.deletedAt IS NULL")
    Page<ProductEntity> findAllNotDeleted(Pageable pageable);

    @Query("SELECT p FROM ProductEntity p  WHERE p.id=:id AND p.deletedAt IS NULL")
    Optional<ProductEntity> findByIDProduct(@Param("id") UUID id);

    @Query("SELECT p FROM ProductEntity p JOIN p.status s WHERE p.deletedAt IS NULL AND s.title = :status")
    Page<ProductEntity> findAllStatus(@Param("status") String status, Pageable pageable);


    @Query("SELECT p FROM ProductEntity p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND p.deletedAt IS NULL")
    Page<ProductEntity> searchProducts(@Param("search") String search, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p JOIN p.status s WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND s.title = :status AND p.deletedAt IS NULL")
    Page<ProductEntity> searchProductsStatus(@Param("search") String search, @Param("status") String status, Pageable pageable);

}
