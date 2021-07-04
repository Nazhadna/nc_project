package com.company.nc_project.repository;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.Country;
import com.company.nc_project.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishRepository extends CrudRepository<Dish, UUID> {

    Iterable<Dish> findAllByCountry(Country country);
}
