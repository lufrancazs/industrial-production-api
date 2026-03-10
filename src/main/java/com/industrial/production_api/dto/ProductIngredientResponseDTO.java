package com.industrial.production_api.dto;

public record ProductIngredientResponseDTO(Long id,
                                           Long productId,
                                           String productName,
                                           Long rawMaterialId,
                                           String rawMaterialName,
                                           Long quantity) {
}
