package com.company.nc_project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lifetime", nullable = false)
    private int lifetime;

    @Column(name = "place", nullable = false)
    private String place;

    public int getLifetime() {
        return lifetime;
    }
}
