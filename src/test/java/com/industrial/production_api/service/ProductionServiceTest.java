package com.industrial.production_api.service;

import com.industrial.production_api.dto.ProductionReduceDTO;
import com.industrial.production_api.entities.Product;
import com.industrial.production_api.entities.ProductIngredient;
import com.industrial.production_api.entities.RawMaterial;
import com.industrial.production_api.repository.ProductRepository;
import com.industrial.production_api.repository.RawMaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductionServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private RawMaterialRepository rawMaterialRepository;
    @InjectMocks
    private ProductionService productionService;

    @Test
    public void shouldReturnProductWhenStockIsSufficient(){
        RawMaterial steel = new RawMaterial();
        steel.setId(1L);
        steel.setName("Steel");
        steel.setStockQuantity(100L);

        ProductIngredient ingredient = new ProductIngredient();
        ingredient.setRawMaterial(steel);
        ingredient.setQuantity(10L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Gear");
        product.setPrice(new BigDecimal("90.00"));
        product.setIngredients(List.of(ingredient));
        ingredient.setProduct(product);

        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));
        when(productRepository.findAll(any(Sort.class))).thenReturn(List.of(product));

        ProductionReduceDTO result = productionService.productionSimulation();

        assertThat(result.product()).hasSize(1);
        assertThat(result.product().getFirst().productName()).isEqualTo("Gear");
        assertThat(result.product().getFirst().producibleQuantity()).isEqualTo(10L);
        assertThat(result.product().getFirst().unitValue()).isEqualByComparingTo("90.00");
        assertThat(result.product().getFirst().totalValue()).isEqualByComparingTo("900.00");
        assertThat(result.valueTotalGeneral()).isEqualByComparingTo("900.00");
    }
}
