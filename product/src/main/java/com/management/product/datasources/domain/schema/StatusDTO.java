package com.management.product.datasources.domain.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDTO {
    private UUID id;
    private String title;
}
