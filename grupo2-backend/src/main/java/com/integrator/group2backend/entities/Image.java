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

    @OneToOne(mappedBy = "featureImage")
    @JsonIgnore
    private Feature feature;

    @OneToOne(mappedBy = "categoryImage")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
