package com.industrial.production_api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductionDTO(Long productId,
                            String productName,
                            Long producibleQuantity,
                            BigDecimal unitValue,
                            BigDecimal totalValue) {
}
