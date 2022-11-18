package com.integrator.group2backend.controller;

import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.service.ReservationService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    public static final Logger logger = Logger.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        Reservation createdReservation = reservationService.addReservation(reservation);
        logger.info("Se ha registrado una nueva reserva.");
        return ResponseEntity.ok(createdReservation);
    }
    @GetMapping
    public ResponseEntity<List<Reservation>> listAllReservations() {
        List<Reservation> searchedReservations = reservationService.listAllReservations();
        if (!(searchedReservations.isEmpty())) {
            logger.info("Se listaron todas las reservas.");
            return ResponseEntity.ok(searchedReservations);
        } else {
            logger.error("Error al listar todas las reservas.");
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> seachReservationById(@PathVariable("id") Long reservationId){
        Optional<Reservation> reservationFound = reservationService.searchReservationById(reservationId);
        if(reservationFound.isPresent()){
            logger.info("Se listo la reserva con id " + reservationId);
            return ResponseEntity.ok(reservationFound.get());
        }else{
            logger.error("Error al listar la reserva con id " + reservationId);
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> seachReservationByUserId(@PathVariable("id") Long userId){
        List<Reservation> reservationFound = reservationService.findReservationByUserId(userId);
        if(!reservationFound.isEmpty()){
            logger.info("Se listaron todas las reservas del usuario con id " + userId);
            return ResponseEntity.ok(reservationFound);
        }else{
            logger.error("Error al listar todas las reservas ddel usuario con id " + userId);
            return ResponseEntity.badRequest().build();
        }
    }
    @RequestMapping(params = {"checkInDate" , "checkOutDate"})
    public ResponseEntity<List<Reservation>> findReservationsByCheckInDateAndCheckOutDate(@RequestParam String checkInDate, @RequestParam String checkOutDate) throws Exception{
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date formattedCheckInDate = dateFormatter.parse(checkInDate);
        Date formattedCheckOutDate = dateFormatter.parse(checkOutDate);
        List<Reservation> reservationsFounded = reservationService.findReservationsByCheckInDateAndCheckOutDate(formattedCheckInDate,formattedCheckOutDate);
        if(!reservationsFounded.isEmpty()){
            logger.info("Se encontraron reservas en el rango de fechas correspondiente.");
            return ResponseEntity.ok(reservationsFounded);
        }
        logger.error("Error al buscar reservas en el rango de fechas correspondiente.");
        return ResponseEntity.badRequest().build();
    }

}
