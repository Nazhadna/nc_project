package com.company.nc_project.repository;

import com.company.nc_project.model.DishesProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishesProductRepository extends CrudRepository<DishesProduct, UUID> {
}
