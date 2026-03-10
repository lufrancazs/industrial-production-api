package com.industrial.production_api.dto;

public record ProductIngredientRequestDTO(Long productId,
                                          Long rawMaterialId,
                                          Long quantity) {
}