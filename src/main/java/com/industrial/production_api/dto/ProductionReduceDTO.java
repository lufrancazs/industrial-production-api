package com.industrial.production_api.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductionReduceDTO(List<ProductionDTO> product,
                                  BigDecimal valueTotalGeneral) {
}
