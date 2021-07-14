package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.StoredProduct;
import com.company.nc_project.model.Product;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.repository.ProductRepository;
import com.company.nc_project.repository.StoredProductRepository;
import io.swagger.annotations.ApiOperation;
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
    StoredProductRepository storedProductRepository;

    @GetMapping()
    @ApiOperation(value = "show all products")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping()
    @ApiOperation(value = "add stored product")
    public StoredProduct addStoredProduct(@RequestBody StoredProduct storedProduct) {
        return storedProductRepository.save(storedProduct);
    }

    @PostMapping("/expired_products/{client_id}/by_client")
    @ApiOperation(value = "show client's expired products")
    public Set<StoredProduct> getExpiredProduct(@PathVariable(value = "client_id") UUID clientId) {
        Set<StoredProduct> expiredProduct = new HashSet<>();
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        for (StoredProduct storedProduct : storedProductRepository.getAllByClient(client)) {
            if (storedProduct.getExpirationDate().before(new Date()))
                expiredProduct.add(storedProduct);
        }
        return expiredProduct;
    }

    @DeleteMapping("/{storedProductId}")
    @ApiOperation(value = "delete stored product")
    public void deleteStoredProduct(@PathVariable(value = "storedProductId") UUID storedProductId) {
        storedProductRepository.deleteById(storedProductId);
    }

    @PostMapping("/{client_id}/by_client")
    @ApiOperation(value = "get client's stored products")
    public Set<StoredProduct> getStoredProductByClient(@PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        return storedProductRepository.getAllByClient(client);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}
