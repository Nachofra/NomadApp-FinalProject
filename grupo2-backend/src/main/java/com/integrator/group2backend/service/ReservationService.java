package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ReservationDTO;
import com.integrator.group2backend.dto.ReservationSimpleDTO;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.repository.ReservationRepository;
import com.integrator.group2backend.utils.MapperService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ProductService productService;
    private final MapperService mapperService;
    public static final Logger logger = Logger.getLogger(ReservationService.class);

    public ReservationService(ReservationRepository reservationRepository, MapperService mapperService, ProductService productService) {
        this.reservationRepository = reservationRepository;
        this.mapperService = mapperService;
        this.productService = productService;
    }
    public List<ReservationDTO> listAllReservations() {
        List<Reservation> searchedReservations = reservationRepository.findAll();
        if (searchedReservations.isEmpty()){
            logger.error("Error al listar todas las reservas.");
        }else {
            logger.info("Se listaron todas las reservas.");
        }
        return this.mapperService.mapList(searchedReservations, ReservationDTO.class);
    }
    public Optional<ReservationDTO> searchReservationById(Long id) {
        Optional reservationFound = reservationRepository.findById(id);
        if (reservationFound.isEmpty()){
            logger.error("Error al listar la reserva con id " + id);
        }else {
            logger.info("Se listo la reserva con id " + id);
        }
        return reservationFound;
    }
    public List<ReservationDTO> findReservationByUserId(Long user_id) {
        List<Reservation> reservationsByUser = this.reservationRepository.findReservationByUserId(user_id);
        List<ReservationDTO> dtoReservationsByUser = this.mapperService.mapList(reservationsByUser, ReservationDTO.class);
        if (reservationsByUser.isEmpty()){
            logger.error("Error al listar todas las reservas ddel usuario con id " + user_id);
        }else {
            logger.info("Se listaron todas las reservas del usuario con id " + user_id);
        }
        return dtoReservationsByUser;
    }
    public ReservationDTO addReservation(Reservation reservation) {
        Product p = this.productService.searchProductById(reservation.getProduct().getId()).get();
        Double priceForDay = p.getDailyPrice().doubleValue();
        Integer days = (int) (reservation.getCheckOutDate().getTime() - reservation.getCheckInDate().getTime()) / 86400000;
        reservation.setFinalPrice(days * priceForDay);
        logger.info("Se ha registrado una nueva reserva.");
        return this.mapperService.convert(this.reservationRepository.save(reservation), ReservationDTO.class);
    }
    public List<ReservationSimpleDTO> findByProductId(Long productId) {
        List<Reservation> reservationsById = this.reservationRepository.findReservationByProductId(productId);
        if (reservationsById.isEmpty()) {
            logger.error("Error al listar todas las reservas del producto con id " + productId);
        }else {
            logger.info("Se listaron todas las reservas del producto con id " + productId);
        }
        return this.mapperService.mapList(reservationsById, ReservationSimpleDTO.class);
    }
    public List<ReservationDTO> findReservationsByCheckInDateAndCheckOutDate(String checkInDate, String checkOutDate) throws Exception {
        DateTimeFormatter dateTimeFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedCheckInDate = LocalDate.parse(checkInDate, dateTimeFormatter);
        LocalDate formattedCheckOutDate = LocalDate.parse(checkOutDate, dateTimeFormatter);
        List<Reservation> reservationsByDate = this.reservationRepository.findReservationsByCheckInDateAndCheckOutDate(formattedCheckInDate, formattedCheckOutDate);
        if (reservationsByDate.isEmpty()){
            logger.error("Error al buscar reservas en el rango de fechas correspondiente.");
        }else {
            logger.info("Se encontraron reservas en el rango de fechas correspondiente.");
        }
        return this.mapperService.mapList(reservationsByDate, ReservationDTO.class);
    }
}
