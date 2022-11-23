package com.integrator.group2backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
@Getter
@Setter
public class ReservationSimpleDTO {
    private Long id;
    private Double finalPrice;
    private Time checkInTime;
    private Date checkInDate;
    private Date checkOutDate;
}