package com.industrial.production_api.dto;

import java.math.BigDecimal;

public record ProductListDTO(Long id,
                             String name,
                             BigDecimal price) {
}
