package com.management.product.internal.service;

import com.management.product.datasources.domain.entity.AdminEntity;
import com.management.product.datasources.domain.schema.AdminRequest;
import com.management.product.datasources.domain.schema.TokenDTO;
import com.management.product.datasources.domain.schema.ValidationRequest;
import com.management.product.datasources.domain.service.AdminServiceIntf;
import com.management.product.datasources.repository.AdminRepository;
import com.management.product.security.JWTUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;


@Service
@Slf4j
public class AdminServiceImpl implements AdminServiceIntf {
    private final AdminRepository adminRepository;

    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        validator = buildDefaultValidatorFactory().getValidator();
    }


    @Override
    public void store(AdminRequest adminRequest) throws Exception {
        Set<ConstraintViolation<AdminRequest>> violations = validator.validate(adminRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        String password = passwordEncoder.encode(adminRequest.getPassword());

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setUsername(adminRequest.getUsername());
        adminEntity.setPassword(password);
        if(adminRequest.getRole().contains("admin")){
            adminEntity.setRole(AdminEntity.Role.ADMINISTRATOR);
        }
        if(adminRequest.getRole().contains("user")){
            adminEntity.setRole(AdminEntity.Role.ACCOUNT);
        }
        adminRepository.save(adminEntity);
    }

    @Override
    public void modify(UUID uuid, AdminRequest adminRequest) throws Exception {
        Set<ConstraintViolation<AdminRequest>> violations = validator.validate(adminRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        String password = passwordEncoder.encode(adminRequest.getPassword());
        AdminEntity existingAdmin = adminRepository.findById(uuid).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        existingAdmin.setUsername(adminRequest.getUsername());
        existingAdmin.setPassword(password);
        adminRepository.save(existingAdmin);
    }

    @Override
    public void remove(UUID uuid) throws Exception {
        AdminEntity existingAdmin = adminRepository.findById(uuid).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        existingAdmin.setDeletedAt(LocalDateTime.now());
        adminRepository.save(existingAdmin);
    }

    @Override
    public TokenDTO validation(ValidationRequest validationRequest) {
        AdminEntity admin = adminRepository.findByUsername(validationRequest.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if(!passwordEncoder.matches(validationRequest.getPassword(), admin.getPassword())){
            throw new RuntimeException("Invalid Username or Password");
        }

        String tokenize = jwtUtils.generateJWTToken(admin);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRole(admin.getRole().toString());
        tokenDTO.setToken(tokenize);
        return  tokenDTO;
    }
}
