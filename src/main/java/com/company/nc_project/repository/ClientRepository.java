package com.company.nc_project.repository;

import com.company.nc_project.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
}
