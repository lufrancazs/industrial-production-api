package com.industrial.production_api.service;

import com.industrial.production_api.dto.RawMaterialDTO;
import com.industrial.production_api.entities.RawMaterial;
import com.industrial.production_api.repository.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<RawMaterialDTO> findAll(){
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();

        return rawMaterialList
                .stream()
                .map(this::toDto)
                .toList();
    }

    public RawMaterialDTO findById(Long id){
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

        return toDto(rawMaterial);
    }

    @Transactional
    public RawMaterialDTO save(RawMaterialDTO dto){
        RawMaterial rawMaterial = new RawMaterial();

        rawMaterial.setName(dto.name());
        rawMaterial.setStockQuantity(dto.stockQuantity());

        rawMaterial = rawMaterialRepository.save(rawMaterial);
        return toDto(rawMaterial);
    }

    @Transactional
    public List<RawMaterialDTO> saveAll(List<RawMaterialDTO> dtos){
        return dtos.stream()
                .map(this::save)
                .toList();
    }

    @Transactional
    public RawMaterialDTO update(Long id, RawMaterialDTO dto){
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

        rawMaterial.setName(dto.name());
        rawMaterial.setStockQuantity(dto.stockQuantity());

        rawMaterial = rawMaterialRepository.save(rawMaterial);
        return toDto(rawMaterial);
    }

    @Transactional
    public void delete(Long id){
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

        rawMaterialRepository.delete(rawMaterial);
    }

    public RawMaterialDTO toDto(RawMaterial rawMaterial){
        return new RawMaterialDTO(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getStockQuantity()
        );
    }
}
