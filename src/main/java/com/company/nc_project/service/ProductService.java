package com.company.nc_project.service;

import com.company.nc_project.model.StoredProduct;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ProductService {

    public Set<StoredProduct> getStoredProduct(Set<StoredProduct> storedProducts){
        Set<StoredProduct> expiredProduct = new HashSet<>();
        Date today = new Date();
        for (StoredProduct storedProduct : storedProducts) {
            if (storedProduct.getExpirationDate().before(today))
                expiredProduct.add(storedProduct);
        }
        return expiredProduct;
    }
}
