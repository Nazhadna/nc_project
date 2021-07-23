package com.company.nc_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "dish_product")
@Data
public class DishesProduct {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "quantity", nullable = false)
    private double quantity;
}
