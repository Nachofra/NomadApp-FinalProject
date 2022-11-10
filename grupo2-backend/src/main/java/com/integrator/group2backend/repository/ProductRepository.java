package com.integrator.group2backend.repository;

import com.integrator.group2backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCityId(Long id);
    List<Product> findByCategoryId(Long id);
    List<Product> findByCityIdAndCategoryId(Long city_id, Long category_id);
}
