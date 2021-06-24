package com.company.nc_project.controllers;

import com.company.nc_project.model.Dish;
import com.company.nc_project.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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

    @PostMapping("/update")
    public Dish updateDish(@RequestBody Dish dish)  {
        return dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    public String deleteDish(@PathVariable(value = "id") UUID dishId) throws EntityNotFoundException {

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found for id : " + dishId));

        dishRepository.delete(dish);
        return "Dish " + dish.getName() + " deleted";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException e) {
        return "error: " + e.getMessage();
    }
}


