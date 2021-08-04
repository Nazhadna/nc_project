package com.company.nc_project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table(name = "country")
@Data
public class Country {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;
}
