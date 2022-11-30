package com.integrator.group2backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
@Getter
@Setter
public class ReservationSimpleDTO {
    private Long id;
    private Double finalPrice;
    private Time checkInTime;
    private Date checkInDate;
    private Date checkOutDate;
}
