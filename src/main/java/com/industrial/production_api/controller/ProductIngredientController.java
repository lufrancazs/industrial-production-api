package com.industrial.production_api.controller;

import com.industrial.production_api.dto.ProductIngredientRequestDTO;
import com.industrial.production_api.dto.ProductIngredientResponseDTO;
import com.industrial.production_api.entities.ProductIngredient;
import com.industrial.production_api.service.ProductIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-ingredients")
public class ProductIngredientController {

    private final ProductIngredientService productIngredientService;

    public ProductIngredientController(ProductIngredientService productIngredientService) {
        this.productIngredientService = productIngredientService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductIngredientResponseDTO>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productIngredientService.findByProduct(productId));
    }

    @PostMapping
    public ResponseEntity<ProductIngredientResponseDTO> create(@RequestBody ProductIngredientRequestDTO dto) {
        ProductIngredientResponseDTO created = productIngredientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productIngredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}