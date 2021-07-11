package com.company.nc_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "stored_items")
@Data
public class StoredItem {
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;
}
