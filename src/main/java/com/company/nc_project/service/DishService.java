package com.company.nc_project.service;

import com.company.nc_project.model.Dish;
import com.company.nc_project.model.Product;
import com.company.nc_project.model.StoredProduct;

import java.util.Set;
import java.util.stream.Collectors;

public class DishService {

    public Set<Dish> getAvailableDishes(Set<Dish> dishes, Set<StoredProduct> storedProducts) {
        Set<Product> clientsProducts = storedProducts.stream().map(StoredProduct::getProduct).collect(Collectors.toSet());
        Set<Dish> availableDishes = dishes.stream()
                .filter(dish -> clientsProducts.containsAll(dish.getProducts())).collect(Collectors.toSet());
        return availableDishes;
    }

    public Set<Dish> getDishesByProducts(Set<Dish> dishes, Set<Product> products){
        Set<Dish> availableDishes = dishes.stream()
                .filter(dish -> products.containsAll(dish.getProducts())).collect(Collectors.toSet());
        return availableDishes;
    }
}

