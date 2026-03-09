package com.industrial.production_api.repository;

import com.industrial.production_api.entities.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long> {
}
