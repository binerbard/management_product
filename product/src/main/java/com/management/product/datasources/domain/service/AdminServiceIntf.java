package com.management.product.datasources.domain.service;

import com.management.product.datasources.domain.schema.AdminRequest;
import com.management.product.datasources.domain.schema.TokenDTO;
import com.management.product.datasources.domain.schema.ValidationRequest;

import java.util.UUID;

public interface AdminServiceIntf {
    void store(AdminRequest adminRequest) throws Exception;
    void modify(UUID uuid,AdminRequest adminRequest) throws Exception;
    void remove(UUID uuid) throws Exception;

    TokenDTO validation(ValidationRequest validationRequest);
}
