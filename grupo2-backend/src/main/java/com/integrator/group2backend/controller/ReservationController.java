package com.integrator.group2backend.controller;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> seachReservationById(@PathVariable("id") Long reservationId){
        Optional<Reservation> reservationFound = reservationService.searchReservationById(reservationId);
        if(reservationFound.isPresent()){
            return ResponseEntity.ok(reservationFound.get());
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> seachReservationByUserId(@PathVariable("id") Long userId){
        List<Reservation> reservationFound = reservationService.findReservationByUserId(userId);
        if(!reservationFound.isEmpty()){
            return ResponseEntity.ok(reservationFound);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        Reservation createdReservation = reservationService.addReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }
}
