package com.industrial.production_api.service;

import com.industrial.production_api.dto.ProductDTO;
import com.industrial.production_api.dto.ProductIngredientDTO;
import com.industrial.production_api.dto.ProductListDTO;
import com.industrial.production_api.entities.Product;
import com.industrial.production_api.entities.ProductIngredient;
import com.industrial.production_api.entities.RawMaterial;
import com.industrial.production_api.repository.ProductRepository;
import com.industrial.production_api.repository.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductService(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<ProductListDTO> findAll(){
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(p -> new ProductListDTO(p.getId(),
                p.getName(),
                p.getPrice())).toList();
    }

    public ProductListDTO findByIdReduced(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return new ProductListDTO(product.getId(),
                product.getName(),
                product.getPrice());
    }

    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return toDTO(product);
    }


    @Transactional
    public ProductDTO save(ProductDTO dto){
        Product product = new Product();
        List<ProductIngredient> productRawMaterials = new ArrayList<>();

        product.setName(dto.name());
        product.setPrice(dto.price());

        for(ProductIngredientDTO listProductIngredientDTO : dto.productIngredients()) {
            RawMaterial rawMaterial = rawMaterialRepository
                    .findById(listProductIngredientDTO.rawMaterialId())
                    .orElseThrow(() -> new RuntimeException("Raw material not found"));

            ProductIngredient productRawMaterial = new ProductIngredient();
            productRawMaterial.setProduct(product);
            productRawMaterial.setRawMaterial(rawMaterial);
            productRawMaterial.setQuantity(listProductIngredientDTO.quantity());

            productRawMaterials.add(productRawMaterial);
        }

        product.setIngredients(productRawMaterials);

        Product save = productRepository.save(product);

        return toDTO(save);
    }

    @Transactional
    public List<ProductDTO> saveAll(List<ProductDTO> dtos){
        return dtos.stream()
                .map(this::save)
                .toList();
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setName(dto.name());
        product.setPrice(dto.price());

        product.getIngredients().clear();

        for(ProductIngredientDTO productIngredientDTO : dto.productIngredients()){
            RawMaterial rawMaterial = rawMaterialRepository.findById(productIngredientDTO.rawMaterialId())
                    .orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

            ProductIngredient relationship = new ProductIngredient();
            relationship.setProduct(product);
            relationship.setRawMaterial(rawMaterial);
            relationship.setQuantity(productIngredientDTO.quantity());

            product.getIngredients().add(relationship);
        }

        Product productUpdated = productRepository.save(product);
        return toDTO(productUpdated);

    }

    @Transactional
    public void delete(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    private ProductDTO toDTO(Product product){

        List<ProductIngredientDTO> productIngredientDTO = product.getIngredients()
                .stream()
                .map(x -> new ProductIngredientDTO(x.getRawMaterial().getId(),
                        x.getQuantity()))
                .toList();

        return new ProductDTO(product.getId(),
                product.getName(),
                product.getPrice(),
                productIngredientDTO);
    }
}

