package com.company.nc_project.controllers;

import com.company.nc_project.model.*;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.repository.ClientsDishRepository;
import com.company.nc_project.repository.DishRepository;
import com.company.nc_project.repository.StoredProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @PostMapping()
    public Dish createDish(@RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @GetMapping("/{id}")
    public Optional<Dish> getDish(@PathVariable(value = "id") UUID dishId) {
        return dishRepository.findById(dishId);
    }

    @PostMapping("/update")
    public Dish updateDish(@RequestBody Dish dish)  {
        return dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable(value = "id") UUID dishId) {
        dishRepository.deleteById(dishId);
    }

    @GetMapping("/{id}/products")
    public Set<Product> getProductsByDish(@PathVariable(value = "id") UUID dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(RuntimeException::new);
        return dish.getProducts();
    }

    @PostMapping("/country")
    public Iterable<Dish> getAllDishesByCountry(@RequestBody Country country) {
        return dishRepository.findAllByCountry(country);
    }

    @PostMapping("/client/{client_id}")
    public Set<Dish> getSavedDishesByClient(@PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        return client.getClientsDishes();
    }

    @PostMapping("/{dish_id}/client/{client_id}")
    public void saveDishForClient(@PathVariable(value = "dish_id") UUID dishId, @PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        clientsDishRepository.save(new ClientsDish(client, dishRepository.findById(dishId).orElseThrow(RuntimeException::new)));
    }

    @PostMapping("/client/{client_id}/available_dishes")
    public Set<Dish> getAvailableDishesByClient(@PathVariable(value = "client_id") UUID clientId) {
        Set<Dish> availableDishes = new HashSet<>();
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        for (Dish dish: client.getClientsDishes())
            ProductNotFound: {
                for (Product product: dish.getProducts())
                    ProductFound: {
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
    public Set<Dish> getDishesByProducts(@RequestBody Set<Product> products) {
        Set<Dish> availableDishes = new HashSet<>();
        Iterable<Dish> dishes = dishRepository.findAll();
        for (Dish dish: dishes)
            NoProduct:{
                for (Product product:dish.getProducts()) {
                    if (!products.contains(product))
                        break NoProduct;
                }
                availableDishes.add(dish);
            }
        return availableDishes;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}


