package com.company.nc_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "client_dish")
@Data
public class ClientsDish  {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public ClientsDish() {
    }

    public ClientsDish(Client client, Dish dish) {
        this.client = client;
        this.dish = dish;
    }
}
