package com.industrial.production_api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "raw_material")
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Long stockQuantity;

    @OneToMany(mappedBy = "rawMaterial")
    private List<ProductIngredient> products = new ArrayList<>();

    public RawMaterial(){
    }

    public RawMaterial(Long id, String name, Long stockQuantity, List<ProductIngredient> products) {
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<ProductIngredient> getProducts() {
        return products;
    }

    public void setProducts(List<ProductIngredient> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RawMaterial that = (RawMaterial) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

