package com.management.product.datasources.domain.usecase;

import com.management.product.datasources.domain.schema.ProductDTO;
import com.management.product.datasources.domain.schema.ProductRequest;
import com.management.product.datasources.domain.service.ProductService;
import com.management.product.scripts.constrains.ResponseMessage;
import com.management.product.scripts.utils.ResponseStatus;
import com.management.product.scripts.utils.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class ProductUseCase {

    private final ProductService productService;

    @Autowired
    public ProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public ResponseEntity<ResponseStatus> storeProduct(ProductRequest productRequest) {
        try {
            productService.store(productRequest);
            log.info("create success");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                    HttpStatus.ACCEPTED.value(),
                    ResponseMessage.StoreSuccess
            ));
        }catch (Exception e){
            log.info("create failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatus(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            ));
        }
    }

    public ResponseEntity<ResponseStatus> modifyProduct(UUID id, ProductRequest productRequest) {
        try {
            productService.modify(id,productRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                    HttpStatus.ACCEPTED.value(),
                    ResponseMessage.UpdateSuccess
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatus(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            ));
        }

    }

    public ResponseEntity<ResponseStatus> removeProduct(UUID id) {
        try {
            productService.remove(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                    HttpStatus.ACCEPTED.value(),
                    ResponseMessage.DeleteSuccess
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatus(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            ));
        }
    }

    public ResponseEntity<ResponseStatus> approveProduct(UUID id) {
        try {
            productService.approve(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                    HttpStatus.ACCEPTED.value(),
                    ResponseMessage.UpdateSuccess
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatus(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            ));
        }
    }

    public ResponseEntity<ResponseStatus> rejectProduct(UUID id) {
        try {
            productService.reject(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                    HttpStatus.ACCEPTED.value(),
                    ResponseMessage.UpdateSuccess
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatus(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            ));
        }
    }
    public ResponseEntity<ResponseWrapper<Optional<ProductDTO>>> findByID(UUID id) {
        Optional<ProductDTO> existingProduct = productService.findById(id);

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponseWrapper<>(
                            HttpStatus.ACCEPTED.value(),
                            HttpStatus.ACCEPTED.toString(),
                            existingProduct
                    ));

    }


    public ResponseEntity<ResponseWrapper<Page<ProductDTO>>> listProduct(String search, int page, int limit) {

        Page<ProductDTO> productList = productService.list(search, page, limit);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseWrapper<>(
                        HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.toString(),
                        productList
                ));
    }

    public ResponseEntity<ResponseWrapper<Page<ProductDTO>>> listProductStatus(String search, String status, int page, int limit) {

        Page<ProductDTO> productList = productService.listStatus(search,status, page, limit);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseWrapper<>(
                        HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.toString(),
                        productList
                ));
    }
}
