package com.integrator.group2backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationPublicDTO {
    private Long id;
    public LocalDate checkInDate;
    public LocalDate checkOutDate;
    private ProductSimpleDTO product;
}
