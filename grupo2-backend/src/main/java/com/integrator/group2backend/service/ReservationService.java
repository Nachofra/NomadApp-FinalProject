package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.PolicyItem;
import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

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

    public Reservation addReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
}
