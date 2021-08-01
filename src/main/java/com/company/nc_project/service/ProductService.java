package com.company.nc_project.service;

import com.company.nc_project.model.StoredProduct;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public Set<StoredProduct> getStoredProduct(Set<StoredProduct> storedProducts){
        Date today = new Date();
        Set<StoredProduct> expiredProduct = storedProducts.stream()
                .filter(storedProduct -> storedProduct.getExpirationDate().before(today)).collect(Collectors.toSet());
        return expiredProduct;
    }
}
