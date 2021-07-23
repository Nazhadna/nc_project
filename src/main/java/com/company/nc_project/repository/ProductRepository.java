package com.company.nc_project.repository;

import com.company.nc_project.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    @Query(value = "SELECT id, name, lifetime, place_id, units_id FROM product JOIN " +
            "(SELECT dish.product_id FROM (SELECT product_id, quantity FROM dish_product WHERE dish_id=?2) AS dish " +
            "LEFT JOIN (SELECT product_id, SUM(quantity) AS quantity FROM stored_product  WHERE client_id=?1 GROUP BY product_id) " +
            "AS available_products ON dish.product_id=available_products.product_id WHERE available_products.product_id IS NULL " +
            "OR dish.quantity>available_products.quantity) AS needed_products " +
            "ON product.id=needed_products.product_id;", nativeQuery = true)
    Set<Product> getNeededProducts(UUID client_id, UUID dish_id);
}
