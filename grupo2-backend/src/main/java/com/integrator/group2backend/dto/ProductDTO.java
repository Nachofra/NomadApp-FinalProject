package com.integrator.group2backend.dto;

import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.entities.Policy;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.stream.ImageInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private Integer rooms;
    private Integer beds;
    private Integer bathrooms;
    private Integer guests;
    private Float dailyPrice;
    private String address;
    private Integer number;
    private Integer floor;
    private String apartment;
    private Float latitude;
    private Float longitude;
    private String cityName;
    private String countryName;
    private List<Policy> policies;
}
