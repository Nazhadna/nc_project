package com.company.nc_project.service;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.Dish;
import com.company.nc_project.model.Product;
import com.company.nc_project.model.StoredProduct;
import org.aspectj.apache.bcel.generic.InstructionConstants;

import java.util.HashSet;
import java.util.Set;

public class DishService {

    public Set<Product> getProductsToBuy(Dish dish, Set<StoredProduct> storedProducts) {
        Set<Product> productsToBuy = new HashSet<>();
        for (Product product : dish.getProducts()) {
            ProductFound:
            {
                for (StoredProduct storedProduct : storedProducts) {
                    if (product.getId() == storedProduct.getProduct().getId())
                        break ProductFound;
                }
                productsToBuy.add(product);
            }
        }
        return productsToBuy;
    }

    public Set<Dish> getAvailableDishes(Set<Dish> dishes, Set<StoredProduct> storedProducts) {
        Set<Dish> availableDishes = new HashSet<>();
        for (Dish dish : dishes)
            ProductNotFound:{
                for (Product product : dish.getProducts())
                    ProductFound:{
                        for (StoredProduct storedProduct : storedProducts) {
                            if (product.getId() == storedProduct.getProduct().getId())
                                break ProductFound;
                        }
                        break ProductNotFound;
                    }
                availableDishes.add(dish);
            }
        return availableDishes;
    }

    public Set<Dish> getDishesByProducts(Iterable<Dish> dishes, Set<Product> products){
        Set<Dish> availableDishes = new HashSet<>();
        for (Dish dish : dishes)
            NoProduct:{
                for (Product product : dish.getProducts()) {
                    if (!products.contains(product))
                        break NoProduct;
                }
                availableDishes.add(dish);
            }
        return availableDishes;
    }
}

