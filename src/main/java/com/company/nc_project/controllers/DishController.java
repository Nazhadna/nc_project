package com.company.nc_project.controllers;

import com.company.nc_project.filter.Filter;
import com.company.nc_project.model.*;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.repository.ClientsDishRepository;
import com.company.nc_project.repository.DishRepository;
import com.company.nc_project.repository.StoredProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DishRepository dishRepository;

    @Autowired
    ClientsDishRepository clientsDishRepository;

    @Autowired
    StoredProductRepository storedProductRepository;

    @GetMapping()
    @ApiOperation(value = "show all dishes")
    public Iterable<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @PostMapping()
    @ApiOperation(value = "create dish")
    public Dish createDish(@RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get dish by id")
    public Optional<Dish> getDish(@PathVariable(value = "id") UUID dishId) {
        return dishRepository.findById(dishId);
    }

    @PostMapping("/update")
    @ApiOperation(value = "update dish")
    public Dish updateDish(@RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete dish")
    public void deleteDish(@PathVariable(value = "id") UUID dishId) {
        dishRepository.deleteById(dishId);
    }

    @GetMapping("/{id}/products")
    @ApiOperation(value = "show products from dish")
    public Set<Product> getProductsByDish(@PathVariable(value = "id") UUID dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"));
        return dish.getProducts();
    }

    @PostMapping("/{dish_id}/client/{client_id}/products/old")
    @ApiOperation(value = "show products to buy for dish")
    public Set<Product> getNeededProductsForDishesByClientOld(@PathVariable(value = "client_id") UUID clientId, @PathVariable(value = "dish_id") UUID dishId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"));
        Set<Product> productsToBuy = new HashSet<>();
        for (Product product : dish.getProducts())
            ProductFound:{
                for (StoredProduct storedProduct : storedProductRepository.getAllByClient(client)) {
                    if (product.getId() == storedProduct.getProduct().getId())
                        break ProductFound;
                }
                productsToBuy.add(product);
            }
        return productsToBuy;
    }

    @PostMapping("/{dish_id}/client/{client_id}/products")
    @ApiOperation(value = "show products to buy for dish")
    public Set<Product> getNeededProductsForDishesByClient(
            @PathVariable(value = "client_id") UUID clientId,
            @PathVariable(value = "dish_id") UUID dishId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"));
        Set<Product> productsToBuy = new HashSet<>();
        for (Product product : dish.getProducts()) {
            for (StoredProduct storedProduct : storedProductRepository.getAllByClient(client)) {
                if (product.getId() == storedProduct.getProduct().getId())
                    break;
            }
            productsToBuy.add(product);
        }
        return productsToBuy;
    }

    @PostMapping("/country")
    @ApiOperation(value = "show dishes by country")
    public Iterable<Dish> getAllDishesByCountry(@RequestBody Country country) {
        return dishRepository.findAllByCountry(country);
    }

    @PostMapping("/filter")
    @ApiOperation(value = "show dishes by filter")
    public Iterable<Dish> getAllDishesByFilter(@RequestBody Filter filter) {
        return dishRepository.findAllByCountryAndRecipeContaining(filter.getCountry(), filter.getRecipe());
    }

    @PostMapping("/client/{client_id}")
    @ApiOperation(value = "show client's dishes")
    public Set<Dish> getSavedDishesByClient(@PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        return client.getClientsDishes();
    }

    @PostMapping("/{dish_id}/client/{client_id}")
    @ApiOperation(value = "save dish for client")
    public void saveDishForClient(@PathVariable(value = "dish_id") UUID dishId, @PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        clientsDishRepository.save(new ClientsDish(client, dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"))));
    }

    @PostMapping("/client/{client_id}/available_dishes")
    @ApiOperation(value = "show client's dishes that client can cook from his products")
    public Set<Dish> getAvailableDishesByClient(@PathVariable(value = "client_id") UUID clientId) {
        Set<Dish> availableDishes = new HashSet<>();
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        for (Dish dish : client.getClientsDishes())
            ProductNotFound:{
                for (Product product : dish.getProducts())
                    ProductFound:{
                        for (StoredProduct storedProduct : storedProductRepository.getAllByClient(client)) {
                            if (product.getId() == storedProduct.getProduct().getId())
                                break ProductFound;
                        }
                        break ProductNotFound;
                    }
                availableDishes.add(dish);
            }
        return availableDishes;
    }

    @PostMapping("/by_products")
    @ApiOperation(value = "show dishes that can be made from incoming products")
    public Set<Dish> getDishesByProducts(@RequestBody Set<Product> products) {
        Set<Dish> availableDishes = new HashSet<>();
        Iterable<Dish> dishes = dishRepository.findAll();
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

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error: " + e.getMessage();
    }
}


