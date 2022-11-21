package com.integrator.group2backend.repository;

import com.integrator.group2backend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationByUserId(Long user_id);
    List<Reservation> findReservationByProductId(Long product_id);
    List<Reservation> findReservationsByCheckInDateAndCheckOutDate(Date checkInDate, Date checkOutDate);
    List<Reservation> findReservationsByCheckInDateAndCheckOutDateAndProductId(Date checkInDate, Date checkOutDate, Long product_id);
}
