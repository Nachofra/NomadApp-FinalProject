package com.integrator.group2backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "policy",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PolicyItem> policyItems;

    @JsonIgnore
    @ManyToMany(mappedBy = "policies", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<Product>();

}
