package com.management.product.datasources.domain.schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidationRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
