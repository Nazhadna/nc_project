package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.StoredProduct;
import com.company.nc_project.model.Product;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.repository.ProductRepository;
import com.company.nc_project.repository.StoredItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoredItemRepository storedItemRepository;

    @GetMapping()
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping()
    public StoredProduct addStoredProduct(@RequestBody StoredProduct storedProduct) {
        return storedItemRepository.save(storedProduct);
    }

    @PostMapping("/expired_products/{client_id}/by_client")
    public Set<StoredProduct> getExpiredProduct(@PathVariable(value = "client_id") UUID clientId) {
        Set<StoredProduct> expiredProduct = new HashSet<>();
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        for (StoredProduct storedProduct:client.getClientsStoredProducts()) {
            if (storedProduct.getExpirationDate().before(new Date()))
                expiredProduct.add(storedProduct);
        }
        return expiredProduct;
    }

    @DeleteMapping("/{storedProductId}")
    public void deleteStoredProduct(@PathVariable(value = "storedProductId") UUID storedProductId) {
        storedItemRepository.deleteById(storedProductId);
    }

    @PostMapping("/{client_id}/by_client")
    public Set<StoredProduct> getStoredProductByClient(@PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        return client.getClientsStoredProducts();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}
