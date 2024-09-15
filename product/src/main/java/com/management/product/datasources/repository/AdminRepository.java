package com.management.product.datasources.repository;

import com.management.product.datasources.domain.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

    Optional<AdminEntity> findByUsername(String username);
}
