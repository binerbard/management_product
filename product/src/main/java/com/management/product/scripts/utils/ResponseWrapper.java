package com.management.product.scripts.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {

    private int code;
    private String message;
    private T data;
}
