package com.integrator.group2backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
public class ReservationDTO {
    private Long id;
    private Double finalPrice;
    private Time checkInTime;
    private Date checkInDate;
    private Date checkOutDate;
    private ProductSimpleDTO product;
    private CurrentUserDTO user;
}
