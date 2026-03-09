package com.industrial.production_api.service;

import com.industrial.production_api.dto.ProductionDTO;
import com.industrial.production_api.dto.ProductionReduceDTO;
import com.industrial.production_api.entities.Product;
import com.industrial.production_api.entities.ProductIngredient;
import com.industrial.production_api.entities.RawMaterial;
import com.industrial.production_api.repository.ProductRepository;
import com.industrial.production_api.repository.RawMaterialRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductionService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;


    public ProductionService(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public ProductionReduceDTO productionSimulation() {
        Map<Long, Long> virtualStock = buildVirtualStock();
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));

        List<ProductionDTO> result = new ArrayList<>();
        BigDecimal totalSalesValue = BigDecimal.ZERO;

        for (Product product : products) {
            if (product.getIngredients() == null || product.getIngredients().isEmpty()) continue;

            Long maxQty = calculateMaxProducibleQty(product, virtualStock);

            if (maxQty > 0) {
                deductFromStock(product, maxQty, virtualStock);
                BigDecimal unitPrice = product.getPrice();
                BigDecimal totalValue = unitPrice.multiply(BigDecimal.valueOf(maxQty));
                totalSalesValue = totalSalesValue.add(totalValue);
                result.add(new ProductionDTO(product.getId(), product.getName(), maxQty, unitPrice, totalValue));
            }
        }
        return new ProductionReduceDTO(result, totalSalesValue);
    }

    private Map<Long, Long> buildVirtualStock() {
        Map<Long, Long> virtualStock = new HashMap<>();
        rawMaterialRepository.findAll().forEach(rm -> virtualStock.put(rm.getId(), rm.getStockQuantity()));
        return virtualStock;
    }

    private Long calculateMaxProducibleQty(Product product, Map<Long, Long> virtualStock) {
        Long maxQty = Long.MAX_VALUE;
        for (ProductIngredient ingredient : product.getIngredients()) {
            Long qtyRequired = ingredient.getQuantity();
            if (qtyRequired == null || qtyRequired == 0) continue;
            Long available = virtualStock.getOrDefault(ingredient.getRawMaterial().getId(), 0L);
            maxQty = Math.min(maxQty, available / qtyRequired);
        }
        return maxQty;
    }

    private void deductFromStock(Product product, Long maxQty, Map<Long, Long> virtualStock) {
        for (ProductIngredient ingredient : product.getIngredients()) {
            Long rawMaterialId = ingredient.getRawMaterial().getId();
            Long consumption = maxQty * ingredient.getQuantity();
            virtualStock.put(rawMaterialId, virtualStock.get(rawMaterialId) - consumption);
        }
    }
}
