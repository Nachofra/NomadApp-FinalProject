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
    List<Product> findByCityIdAndCategoryIdAndGuests(Long city_id, Long category_id, Integer guests);

    //@Query(value = "SELECT * FROM product p LEFT JOIN reservation r ON r.product_id = p.id WHERE p.city_id = :city AND r.check_in_date <= :checkInDate OR r.check_out_date >= :checkOutDate OR r.check_in_date IS NULL OR r.check_out_date IS NULL;", nativeQuery = true)
    @Query(value = "SELECT * FROM product p INNER JOIN reservation r ON p.id = r.product_id WHERE p.city_id = :city AND r.check_in_date >= :checkInDate AND r.check_out_date <= :checkOutDate", nativeQuery = true)
    List<Product> searchProductByCityCheckInDateCheckOutDate(@Param("city") Long city, @Param("checkInDate") Date checkInDate, @Param("checkOutDate") Date checkOutDate);
}
