package com.company.nc_project.controllers;

import com.company.nc_project.model.StoredItem;
import com.company.nc_project.model.StoredItemProjection;
import com.company.nc_project.model.Product;
import com.company.nc_project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/product")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @GetMapping("/client/{clientId}/product")
    public Collection<StoredItemProjection> getClientsProducts(@PathVariable(value = "clientId") UUID clientId){
        return productRepository.getStoredItems(clientId);
    }

    @PostMapping("/client/{clientId}/product/{productId}")
    public String addProduct(@PathVariable(value = "clientId") UUID clientId,
                             @PathVariable(value = "productId") UUID productId) {
        productRepository.addStoredItem(clientId, productId);
        return "Success";
    }

    @DeleteMapping("/product/{storedItemId}")
    public String deleteProduct(@PathVariable(value = "storedItemId") UUID storedItemId) {

        productRepository.deleteStoredItem(storedItemId);
        return "Product deleted";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}
