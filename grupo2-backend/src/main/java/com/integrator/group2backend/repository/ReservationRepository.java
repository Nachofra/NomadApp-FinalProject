package com.integrator.group2backend.repository;

import com.integrator.group2backend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationByUserId(Long user_id);

    List<Reservation> findReservationByProductId(Long product_id);

    @Query(value = "SELECT * FROM reservation r WHERE ((r.check_in_date between :checkInDate and :checkOutDate) OR (r.check_out_date between :checkInDate and :checkOutDate));", nativeQuery = true)
    List<Reservation> findReservationsByCheckInDateAndCheckOutDate(@Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);

    List<Reservation> findReservationsByCheckInDateAndCheckOutDateAndProductId(Date checkInDate, Date checkOutDate, Long product_id);
}
