package com.integrator.group2backend.dto;

import com.integrator.group2backend.entities.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductViewDTO {
    @Id
    private Long id;
    private String title;
    private String description;
    private Integer rooms;
    private Integer beds;
    private Integer bathrooms;
    private Integer guests;
    private Float dailyPrice;
    private Float latitude;
    private Float longitude;
    private Category category;
    private String cityName;
    private String countryName;
    private Set<Feature> features;
    private Set<Policy> policies;
    private List<String> images;
}