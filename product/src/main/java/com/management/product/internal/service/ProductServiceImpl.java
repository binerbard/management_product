package com.management.product.internal.service;

import com.management.product.datasources.domain.entity.ProductEntity;
import com.management.product.datasources.domain.entity.StatusEntity;
import com.management.product.datasources.domain.schema.ProductDTO;
import com.management.product.datasources.domain.schema.ProductRequest;
import com.management.product.datasources.domain.service.ProductService;
import com.management.product.datasources.repository.ProductRepository;
import com.management.product.datasources.repository.StatusRepository;
import com.management.product.scripts.constrains.ResponseMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StatusRepository statusRepository;
    private final Validator validator;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, StatusRepository statusRepository){
        this.productRepository = productRepository;
        this.statusRepository = statusRepository;
        validator = buildDefaultValidatorFactory().getValidator();
    }

    private ProductDTO convertToDTO(ProductEntity product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        if (product.getStatus() != null) {
//            dto.setStatusId(product.getStatus().getId());
            dto.setStatusTitle(product.getStatus().getTitle());
        }
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setDeletedAt(product.getDeletedAt());
        return dto;
    }
    @Override
    public void store(ProductRequest productRequest) throws Exception {
        log.info("store data");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        log.info("after validate");
        StatusEntity pendingStatus = statusRepository.findByTitle("pending");
        ProductEntity product = new ProductEntity();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStatus(pendingStatus);
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
    }

    @Override
    public void modify(UUID id, ProductRequest productRequest) throws Exception {
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        ProductEntity existingProduct = productRepository.findById(id).orElseThrow(()-> new Exception(ResponseMessage.StatusNotFound));

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        productRepository.save(existingProduct);
    }

    @Override
    public void remove(UUID id) throws Exception {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            throw new Exception(ResponseMessage.StatusNotFound);
        }

        ProductEntity product = existingProduct.get();

        product.setDeletedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    @Override
    public void approve(UUID id) throws Exception {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            throw new Exception(ResponseMessage.StatusNotFound);
        }
        StatusEntity approveStatus = statusRepository.findByTitle("approve");

        ProductEntity product = existingProduct.get();
        product.setStatus(approveStatus);

        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    @Override
    public void reject(UUID id) throws Exception {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            throw new Exception(ResponseMessage.StatusNotFound);
        }
        StatusEntity approveStatus = statusRepository.findByTitle("reject");

        ProductEntity product = existingProduct.get();
        product.setStatus(approveStatus);

        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    @Override
    public Optional<ProductDTO> findById(UUID id) {
        return productRepository.findByIDProduct(id).map(this::convertToDTO);
    }

    @Override
    public Page<ProductDTO> list(String search, int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit);
        if (search != null && !search.isEmpty()) {
            return productRepository.searchProducts(search, pageable).map(this::convertToDTO);
        } else {
            return productRepository.findAllNotDeleted(pageable).map(this::convertToDTO);
        }
    }

    @Override
    public Page<ProductDTO> listStatus(String search, String status, int page, int limit) {
        log.info(status);
        Pageable pageable = PageRequest.of(page,limit);
        if (search != null && !search.isEmpty()) {
            log.info("Search with param search and status");
            return productRepository.searchProductsStatus(search,status, pageable).map(this::convertToDTO);
        } else {
            log.info("Search with param status");
            return productRepository.findAllStatus(status,pageable).map(this::convertToDTO);
        }
    }
}
