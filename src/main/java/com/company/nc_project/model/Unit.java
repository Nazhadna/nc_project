package com.company.nc_project.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "units")
@Data
public class Unit {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
}
