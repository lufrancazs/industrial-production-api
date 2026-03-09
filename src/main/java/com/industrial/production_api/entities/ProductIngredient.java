package com.industrial.production_api.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "product_ingredient")
public class ProductIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "raw_material_id", nullable = false)
    private RawMaterial rawMaterial;

    private Long quantity;

    public ProductIngredient(){
    }

    public ProductIngredient(Long id, Product product, RawMaterial rawMaterial, Long quantity) {
        this.id = id;
        this.product = product;
        this.rawMaterial = rawMaterial;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductIngredient that = (ProductIngredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

