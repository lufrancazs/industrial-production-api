package com.industrial.production_api.controller;


import com.industrial.production_api.dto.ProductDTO;
import com.industrial.production_api.dto.ProductListDTO;
import com.industrial.production_api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductListDTO>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping(value = "/{id}/summary")
    public ResponseEntity<ProductListDTO> findByIdReduced(@PathVariable Long id){
        return ResponseEntity.ok(productService.findByIdReduced(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto){
        ProductDTO saveProduct = productService.save(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveProduct);
    }

    @PostMapping(value = "/batch")
    public ResponseEntity<List<ProductDTO>> createProductList(@RequestBody List<ProductDTO> dtos){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.saveAll(dtos));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto){
        ProductDTO updatedProduct = productService.update(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

