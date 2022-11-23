package com.integrator.group2backend.controller;

import com.integrator.group2backend.dto.ReservationDTO;
import com.integrator.group2backend.dto.ReservationSimpleDTO;
import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.service.ReservationService;
import com.integrator.group2backend.utils.MapperService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    public static final Logger logger = Logger.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    private final MapperService mapperService;

    public ReservationController(ReservationService reservationService, MapperService mapperService) {
        this.reservationService = reservationService;
        this.mapperService = mapperService;
    }
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation){
        ReservationDTO createdReservation = reservationService.addReservation(reservation);
        logger.info("Se ha registrado una nueva reserva.");
        return ResponseEntity.ok(createdReservation);
    }
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> listAllReservations() {
        List<ReservationDTO> searchedReservations = reservationService.listAllReservations();
        if (!(searchedReservations.isEmpty())) {
            logger.info("Se listaron todas las reservas.");
            return ResponseEntity.ok(searchedReservations);
        } else {
            logger.error("Error al listar todas las reservas.");
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> seachReservationById(@PathVariable("id") Long reservationId){
        Optional<Reservation> reservationFound = reservationService.searchReservationById(reservationId);
        if(reservationFound.isPresent()){
            logger.info("Se listo la reserva con id " + reservationId);
            return ResponseEntity.ok(this.mapperService.convert(reservationFound.get(), ReservationDTO.class));
        }else{
            logger.error("Error al listar la reserva con id " + reservationId);
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReservationDTO>> seachReservationByUserId(@PathVariable("id") Long userId){
        List<ReservationDTO> reservationFound = reservationService.findReservationByUserId(userId);
        if(!reservationFound.isEmpty()){
            logger.info("Se listaron todas las reservas del usuario con id " + userId);
            return ResponseEntity.ok(reservationFound);
        }else{
            logger.error("Error al listar todas las reservas ddel usuario con id " + userId);
            return ResponseEntity.badRequest().build();
        }
    }
    @RequestMapping(params = {"checkInDate" , "checkOutDate"})
    public ResponseEntity<List<ReservationDTO>> findReservationsByCheckInDateAndCheckOutDate(@RequestParam String checkInDate, @RequestParam String checkOutDate) throws Exception{
        List<ReservationDTO> reservationsFounded = reservationService.findReservationsByCheckInDateAndCheckOutDate(checkInDate, checkOutDate);
        if(!reservationsFounded.isEmpty()){
            logger.info("Se encontraron reservas en el rango de fechas correspondiente.");
            return ResponseEntity.ok(reservationsFounded);
        }
        logger.error("Error al buscar reservas en el rango de fechas correspondiente.");
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<List<ReservationSimpleDTO>> getReservationByProductId(@PathVariable("id") Long productId){
        List<ReservationSimpleDTO> reservationFound = reservationService.findByProductId(productId);
        if(!reservationFound.isEmpty()){
            logger.info("Se listaron todas las reservas del producto con id " + productId);
            return ResponseEntity.ok(reservationFound);
        }
        logger.error("Error al listar todas las reservas del producto con id " + productId);
        return ResponseEntity.badRequest().build();
    }

}
