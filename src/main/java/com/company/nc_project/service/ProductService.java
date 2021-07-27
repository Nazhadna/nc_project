package com.company.nc_project.service;

import com.company.nc_project.model.Dish;
import com.company.nc_project.model.StoredProduct;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductService {

    public Set<StoredProduct> getStoredProduct(Set<StoredProduct> storedProducts){
        Date today = new Date();
        Set<StoredProduct> expiredProduct = storedProducts.stream()
                .filter(storedProduct -> storedProduct.getExpirationDate().before(today)).collect(Collectors.toSet());
        return expiredProduct;
    }
}
