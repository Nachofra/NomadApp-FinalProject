package com.integrator.group2backend.repository;

import com.integrator.group2backend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    //@Query("select c from city c where c.postalCode=?1")
    Optional<City> findByPostalCode(String postalCode);
}
