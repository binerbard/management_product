package com.management.product.internal.routes;


import com.management.product.datasources.domain.schema.ProductRequest;
import com.management.product.datasources.domain.usecase.ProductUseCase;
import com.management.product.scripts.constrains.DataConstrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(DataConstrain.ApiVersion+"/user/product")
public class ProductUserRoutes {
    private final ProductUseCase productUseCase;

    @Autowired
    public ProductUserRoutes(ProductUseCase productUseCase){
        this.productUseCase = productUseCase;
    }


    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        return  productUseCase.storeProduct(productRequest);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id , @RequestBody ProductRequest productRequest){
        return  productUseCase.modifyProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
        return  productUseCase.removeProduct(id);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getByID(@PathVariable UUID id) {
        return productUseCase.findByID(id);
    }

    @GetMapping("/list/status")
    public  ResponseEntity<?> getAllStatus(@RequestParam(required = false) String search,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "100") int limit) {
        return productUseCase.listProductStatus(search,status, page, limit);
    }
}
