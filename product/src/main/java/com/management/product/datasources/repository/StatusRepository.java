package com.management.product.datasources.repository;

import com.management.product.datasources.domain.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, UUID> {

    @Modifying
    @Query("UPDATE StatusEntity c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.id = :id")
    void deletedAtByID(@Param("id") UUID id);

    @Query("SELECT c FROM StatusEntity c WHERE c.title = :title AND c.deletedAt IS NULL")
    StatusEntity findByTitle(@Param("title") String title);

}
