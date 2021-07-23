package com.company.nc_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "lifetime", nullable = false)
    @NotEmpty
    private Integer lifetime;

    @ManyToOne
    @JoinColumn(name = "units_id")
    private Unit unit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    public int getLifetime() {
        return lifetime;
    }
}
