package com.industrial.production_api.dto;

import com.industrial.production_api.entities.ProductIngredient;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(Long id,
                         String name,
                         BigDecimal price,
                         List<ProductIngredientDTO> productIngredients) {
}
