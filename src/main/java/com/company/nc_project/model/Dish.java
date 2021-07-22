package com.company.nc_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "dish")
@Data
public class Dish{

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "recipe", nullable = false)
    private String recipe;

    @ManyToOne
    @JoinTable(
            name = "client_dish",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    @JsonIgnore
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "dish_product",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonIgnore
    Set<Product> products;
}
