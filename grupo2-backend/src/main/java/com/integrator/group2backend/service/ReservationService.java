package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ReservationDTO;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.entities.Reservation;
import com.integrator.group2backend.repository.ProductRepository;
import com.integrator.group2backend.repository.ReservationRepository;
import com.integrator.group2backend.utils.MapperService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final MapperService mapperService;

    public ReservationService(ReservationRepository reservationRepository, MapperService mapperService, ProductRepository productRepository) {
        this.reservationRepository = reservationRepository;
        this.mapperService = mapperService;
        this.productRepository = productRepository;
    }
    public List<ReservationDTO> listAllReservations() {
        List<Reservation> searchedReservations = reservationRepository.findAll();
        return this.mapperService.mapList(searchedReservations, ReservationDTO.class);
    }
    public Optional<Reservation> searchReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    public List<ReservationDTO> findReservationByUserId(Long user_id) {
        return this.mapperService.mapList(this.reservationRepository.findReservationByUserId(user_id), ReservationDTO.class);
    }
    public ReservationDTO addReservation(Reservation reservation) {
        Product p = this.productRepository.findById(reservation.getProduct().getId()).get();
        Double priceForDay = p.getDailyPrice().doubleValue();
        Integer days = (int) (reservation.getCheckOutDate().getTime() - reservation.getCheckInDate().getTime()) / 86400000;
        reservation.setFinalPrice(days * priceForDay);
        return this.mapperService.convert(this.reservationRepository.save(reservation), ReservationDTO.class);
    }
    public List<ReservationDTO> findReservationsByCheckInDateAndCheckOutDate(String checkInDate, String checkOutDate) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedCheckInDate = dateFormatter.parse(checkInDate);
        Date formattedCheckOutDate = dateFormatter.parse(checkOutDate);
        return this.mapperService.mapList(this.reservationRepository.findReservationsByCheckInDateAndCheckOutDate(formattedCheckInDate, formattedCheckOutDate), ReservationDTO.class);
    }
}
