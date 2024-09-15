package com.management.product.internal.routes;

import com.management.product.datasources.domain.schema.ProductRequest;
import com.management.product.datasources.domain.usecase.ProductUseCase;
import com.management.product.scripts.constrains.DataConstrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(DataConstrain.ApiVersion+"/admin/product")
public class ProductAdminRoutes {
    private final ProductUseCase productUseCase;

    @Autowired
    public ProductAdminRoutes(ProductUseCase productUseCase){
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


    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveProduct(@PathVariable UUID id ){
        return  productUseCase.approveProduct(id);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectProduct(@PathVariable UUID id ){
        return  productUseCase.rejectProduct(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
        return  productUseCase.removeProduct(id);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getByID(@PathVariable UUID id) {
        return productUseCase.findByID(id);
    }

    @GetMapping("/list")
    public  ResponseEntity<?> getAll(@RequestParam(required = false) String search,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "100") int limit) {
        return productUseCase.listProduct(search, page, limit);
    }

    @GetMapping("/list/status")
    public  ResponseEntity<?> getAllStatus(@RequestParam(required = false) String search,
                                                                           @RequestParam(required = false) String status,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "100") int limit) {
        return productUseCase.listProductStatus(search,status, page, limit);
    }

}
