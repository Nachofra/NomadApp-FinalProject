package com.integrator.group2backend.repository;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCityId(Long id);
    List<Product> findByCategoryId(Long id);
    List<Product> findByCityIdAndCategoryId(Long city_id, Long category_id);
    @Query(value = "SELECT * FROM product p INNER JOIN reservation r ON r.product_id = p.id WHERE p.city_id = %:city_id% AND r.check_in_date >= %:checkInDate% AND r.check_out_date <= %:checkOutDate%", nativeQuery = true)
    List<Product> searchProductByCityIdCheckInDateCheckOutDate(@Param("city_id") Long city_id, @Param("checkInDate") Date checkInDate, @Param("checkOutDate") Date checkOutDate);
}
