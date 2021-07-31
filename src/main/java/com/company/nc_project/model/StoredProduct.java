package com.company.nc_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "stored_product")
@Data
public class StoredProduct {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoredProduct that = (StoredProduct) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
