package com.industrial.production_api.dto;

import com.industrial.production_api.entities.ProductIngredient;

import java.math.BigDecimal;
import java.util.List;

public record ProductIngredientDTO(Long rawMaterialId,
                                   Long quantity) {
}
