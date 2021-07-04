package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.StoredItem;
import com.company.nc_project.model.Product;
import com.company.nc_project.repository.ProductRepository;
import com.company.nc_project.repository.StoredItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoredItemRepository storedItemRepository;

    @GetMapping("/product")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/stored_item")
    public Iterable<StoredItem> getProductsByClient(@RequestBody Client client) {
        return storedItemRepository.getAllByClient(client);
    }

    @PostMapping("/add_stored_item")
    public StoredItem addProduct(@RequestBody StoredItem storedItem) {
        return storedItemRepository.save(storedItem);
    }

    @DeleteMapping("/product/{storedItemId}")
    public void deleteProduct(@PathVariable(value = "storedItemId") UUID storedItemId) {
        storedItemRepository.deleteById(storedItemId);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}
