package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.PolicyItem;
import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public Optional<Reservation> searchReservationById(Long id){
        return reservationRepository.findById(id);
    }
    public List<Reservation> findReservationByUserId(Long user_id){
        return reservationRepository.findReservationByUserId(user_id);
    }
    public Reservation addReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public List<Reservation> findReservationsByCheckInDateAndCheckOutDate(Date checkInDate, Date checkOutDate){
        List<Reservation> reservationFoundByCheckInDateAndCheckOutDate = reservationRepository.findReservationsByCheckInDateAndCheckOutDate(checkInDate,checkOutDate);
        return reservationFoundByCheckInDateAndCheckOutDate;
    }
}
