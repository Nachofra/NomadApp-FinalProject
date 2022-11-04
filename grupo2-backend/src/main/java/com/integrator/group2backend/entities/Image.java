package com.integrator.group2backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String URL;
    private String name;
    private String description;
    private String extension;

    @JsonIgnore
    @OneToOne(mappedBy = "featureImage", fetch = FetchType.LAZY)
    private Feature feature;

    @JsonIgnore
    @OneToOne(mappedBy = "categoryImage", fetch = FetchType.LAZY)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
