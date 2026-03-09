package com.industrial.production_api.controller;

import com.industrial.production_api.dto.RawMaterialDTO;
import com.industrial.production_api.service.RawMaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/raw-material")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDTO>> findAll(){
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RawMaterialDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok((rawMaterialService.findById(id)));
    }


    @PostMapping
    public ResponseEntity<RawMaterialDTO> create(@RequestBody RawMaterialDTO dto){
        RawMaterialDTO saveRawMaterial = rawMaterialService.save(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveRawMaterial);
    }

    @PostMapping(value = "/batch")
    public ResponseEntity<List<RawMaterialDTO>> createRawMaterialList(@RequestBody List<RawMaterialDTO> dtos){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rawMaterialService.saveAll(dtos));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RawMaterialDTO> updateRawMaterial
            (@PathVariable Long id, @RequestBody RawMaterialDTO dto){
        RawMaterialDTO updatedRawMaterial = rawMaterialService.update(id, dto);
        return ResponseEntity.ok(updatedRawMaterial);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long id){
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

