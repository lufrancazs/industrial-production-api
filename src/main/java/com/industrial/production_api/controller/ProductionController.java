package com.industrial.production_api.controller;

import com.industrial.production_api.dto.ProductionReduceDTO;
import com.industrial.production_api.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping(value = "/simulation")
    public ResponseEntity<ProductionReduceDTO> simulation(){
        return ResponseEntity.ok(productionService.productionSimulation());
    }
}
