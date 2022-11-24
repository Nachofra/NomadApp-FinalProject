package com.integrator.group2backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservationPublicDTO {
    private Long id;
    public Date checkInDate;
    public Date checkOutDate;
    private ProductSimpleDTO product;
}