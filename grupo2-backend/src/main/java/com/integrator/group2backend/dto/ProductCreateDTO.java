package com.integrator.group2backend.dto;

import com.integrator.group2backend.entities.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
public class ProductCreateDTO {
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
    private Long category_id;
    private Long city_id;
    private ArrayList<Long> features_id;
    private ArrayList<Long> policyItems_id;
    private Set<Image> images;
}
