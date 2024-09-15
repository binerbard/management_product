package com.management.product.datasources.domain.service;

import com.management.product.datasources.domain.schema.ProductDTO;
import com.management.product.datasources.domain.schema.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    void store(ProductRequest productRequest) throws Exception;

    void modify(UUID id, ProductRequest productRequest) throws Exception;

    void remove(UUID id) throws Exception;
    void approve(UUID id) throws Exception;
    void reject(UUID id) throws Exception;

    Optional<ProductDTO> findById(UUID id);

    Page<ProductDTO> list(String search, int page, int limit);

    Page<ProductDTO> listStatus(String search, String status, int page, int limit);
}
