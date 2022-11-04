package com.integrator.group2backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "policy_by_product",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "policy_id")
            }
    )
    @JsonIgnore
    Set<Policy> policies = new HashSet<Policy>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "feature_by_product",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "feature_id")
            }
    )
    @JsonIgnore
    Set<Feature> features = new HashSet<Feature>();

}
