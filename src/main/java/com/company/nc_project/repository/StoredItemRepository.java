package com.company.nc_project.repository;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.StoredItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StoredItemRepository extends CrudRepository<StoredItem, UUID> {
    Iterable<StoredItem> getAllByClient(Client client);
}
