package com.management.product.datasources.domain.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private BigInteger price;

    @JsonProperty("status_id")
    private UUID statusId;

    @JsonProperty("status_title")
    private String statusTitle;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("deletedAt")
    private LocalDateTime deletedAt;
}

