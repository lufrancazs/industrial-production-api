package com.industrial.production_api.service;

import com.industrial.production_api.dto.ProductIngredientRequestDTO;
import com.industrial.production_api.dto.ProductIngredientResponseDTO;
import com.industrial.production_api.dto.ProductListDTO;
import com.industrial.production_api.entities.Product;
import com.industrial.production_api.entities.ProductIngredient;
import com.industrial.production_api.entities.RawMaterial;
import com.industrial.production_api.repository.ProductIngredientRepository;
import com.industrial.production_api.repository.ProductRepository;
import com.industrial.production_api.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductIngredientService {

    private final ProductIngredientRepository productIngredientRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductIngredientService(ProductIngredientRepository productIngredientRepository,
                                    ProductRepository productRepository,
                                    RawMaterialRepository rawMaterialRepository) {
        this.productIngredientRepository = productIngredientRepository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<ProductIngredientResponseDTO> findByProduct(Long productId) {
        return productIngredientRepository.findByProductId(productId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public ProductIngredientResponseDTO create(ProductIngredientRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.productId()));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw material not found with id: " + dto.rawMaterialId()));

        ProductIngredient ingredient = new ProductIngredient();
        ingredient.setProduct(product);
        ingredient.setRawMaterial(rawMaterial);
        ingredient.setQuantity(dto.quantity());

        ProductIngredient saved = productIngredientRepository.save(ingredient);
        return toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!productIngredientRepository.existsById(id)) {
            throw new RuntimeException("Product ingredient not found with id: " + id);
        }
        productIngredientRepository.deleteById(id);
    }

    private ProductIngredientResponseDTO toDTO(ProductIngredient ingredient) {
        return new ProductIngredientResponseDTO(
                ingredient.getId(),
                ingredient.getProduct().getId(),
                ingredient.getProduct().getName(),
                ingredient.getRawMaterial().getId(),
                ingredient.getRawMaterial().getName(),
                ingredient.getQuantity()
        );
    }
}