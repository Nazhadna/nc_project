package com.company.nc_project.repository;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.StoredProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface StoredProductRepository extends CrudRepository<StoredProduct, UUID> {
    Set<StoredProduct> getAllByClient(Client client);
}
