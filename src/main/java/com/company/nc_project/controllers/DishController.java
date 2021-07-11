package com.company.nc_project.controllers;

import com.company.nc_project.model.*;
import com.company.nc_project.repository.ClientsDishRepository;
import com.company.nc_project.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    ClientsDishRepository clientsDishRepository;

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

    @PostMapping("/{id}/add_to_client")
    public void saveDishForClient(@PathVariable(value = "id") UUID dishId, @RequestBody Client client) {
        clientsDishRepository.save(new ClientsDish(client, dishRepository.findById(dishId).orElseThrow(RuntimeException::new)));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}


