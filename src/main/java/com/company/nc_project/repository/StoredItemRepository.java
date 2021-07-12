package com.company.nc_project.repository;

import com.company.nc_project.model.StoredProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StoredItemRepository extends CrudRepository<StoredProduct, UUID> {
}
