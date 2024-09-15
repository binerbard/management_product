package com.management.product.datasources.domain.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdminRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^(admin|user)",message = "Status mush be either ADMIN or USER")
    private String role;
}
