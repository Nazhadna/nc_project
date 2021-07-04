package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.Country;
import com.company.nc_project.model.Dish;
import com.company.nc_project.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    DishRepository dishRepository;

    @GetMapping()
    public Iterable<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @PostMapping()
    public Dish createDish(@RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @PostMapping("/country")
    public Iterable<Dish> getAllDishesByCountry(@RequestBody Country country) {
        return dishRepository.findAllByCountry(country);
    }

    @PostMapping("/update")
    public Dish updateDish(@RequestBody Dish dish)  {
        return dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable(value = "id") UUID dishId) {
        dishRepository.deleteById(dishId);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}


