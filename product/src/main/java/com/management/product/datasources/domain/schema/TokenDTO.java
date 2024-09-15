package com.management.product.datasources.domain.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO {

    private String token;
    private String role;
}
