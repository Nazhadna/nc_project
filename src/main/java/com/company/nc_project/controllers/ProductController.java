package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.Product;
import com.company.nc_project.model.StoredProduct;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.repository.ProductRepository;
import com.company.nc_project.repository.StoredProductRepository;
import com.company.nc_project.service.ClientService;
import com.company.nc_project.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoredProductRepository storedProductRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @GetMapping()
    @ApiOperation(value = "show all products")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping()
    @ApiOperation(value = "create product")
    @PreAuthorize("hasAuthority('for_admin')")
    public Product createDish(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping("/stored_product")
    @ApiOperation(value = "add stored product")
    @PreAuthorize("hasAuthority('for_user')")
    public StoredProduct addStoredProduct(HttpServletRequest request, @RequestBody StoredProduct storedProduct) {
        storedProduct.setClient(clientService.getClientFromRequest(request));
        return storedProductRepository.save(storedProduct);
    }

    @PostMapping("/expired_products/by_client")
    @ApiOperation(value = "show client's expired products")
    @PreAuthorize("hasAuthority('for_user')")
    public Set<StoredProduct> getExpiredProduct(HttpServletRequest request) {
        Client client = clientService.getClientFromRequest(request);
        Set<StoredProduct> storedProducts = storedProductRepository.getAllByClient(client);
        return productService.getStoredProduct(storedProducts);
    }

    @DeleteMapping("/{storedProductId}")
    @ApiOperation(value = "delete stored product")
    @PreAuthorize("hasAuthority('for_user')")
    public void deleteStoredProduct(@PathVariable(value = "storedProductId") UUID storedProductId) {
        storedProductRepository.deleteById(storedProductId);
    }

    @PostMapping("/by_client")
    @ApiOperation(value = "get client's stored products")
    @PreAuthorize("hasAuthority('for_user')")
    public Set<StoredProduct> getStoredProductByClient(HttpServletRequest request) {
        Client client = clientService.getClientFromRequest(request);
        return storedProductRepository.getAllByClient(client);
    }

    @PostMapping("/dish/{dish_id}")
    @ApiOperation(value = "show products to buy for dish")
    @PreAuthorize("hasAuthority('for_user')")
    public Set<Product> getNeededProductsForDishesByClient(
            HttpServletRequest request,
            @PathVariable(value = "dish_id") UUID dishId) {
        UUID clientId = clientService.getClientFromRequest(request).getId();
        return productRepository.getNeededProducts(clientId, dishId);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}
