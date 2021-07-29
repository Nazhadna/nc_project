package com.company.nc_project.controllers;

import com.company.nc_project.filter.Filter;
import com.company.nc_project.model.*;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.repository.ClientsDishRepository;
import com.company.nc_project.repository.DishRepository;
import com.company.nc_project.repository.StoredProductRepository;
import com.company.nc_project.service.DishService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.*;

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

    DishService dishService = new DishService();

    @GetMapping()
    @ApiOperation(value = "show all dishes")
    public Iterable<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @PostMapping()
    @ApiOperation(value = "create dish")
    public Dish createDish(@Valid @RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get dish by id")
    public Optional<Dish> getDish(@PathVariable(value = "id") UUID dishId) {
        return dishRepository.findById(dishId);
    }

    @PostMapping("/update")
    @ApiOperation(value = "update dish")
    public Dish updateDish(@Valid @RequestBody Dish dish) {
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

    @PostMapping("/country")
    @ApiOperation(value = "show dishes by country")
    public Iterable<Dish> getAllDishesByCountry(@RequestBody Country country) {
        return dishRepository.findAllByCountry(country);
    }

    @PostMapping("/filter")
    @ApiOperation(value = "show dishes by filter")
    public Iterable<Dish> getAllDishesByFilter(@RequestBody Filter filter) {
        return dishRepository.findAllByFilter(filter);
    }

    @PostMapping("/client/{client_id}")
    @ApiOperation(value = "show client's dishes")
    public Collection<Dish> getSavedDishesByClient(@PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        return dishRepository.getAllByClientContaining(client);
    }

    @PostMapping("/{dish_id}/client/{client_id}")
    @ApiOperation(value = "save dish for client")
    public void saveDishForClient(@PathVariable(value = "dish_id") UUID dishId, @PathVariable(value = "client_id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        clientsDishRepository.save(new ClientsDish(client, dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"))));
    }

    @PostMapping("/client/{client_id}/available_dishes/{all_dishes?}")
    @ApiOperation(value = "show client's (or all) dishes that client can cook from his products")
    public Set<Dish> getAvailableDishesByClient(@PathVariable(value = "client_id") UUID clientId,
                                                @PathVariable(value = "all_dishes?") boolean allDishes) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("No such client"));
        Set<StoredProduct> storedProducts = storedProductRepository.getAllByClient(client);
        return (allDishes) ? dishService.getAvailableDishes(dishRepository.findAll(), storedProducts)
                : dishService.getAvailableDishes(dishRepository.getAllByClientContaining(client), storedProducts);
    }

    @PostMapping("/by_products")
    @ApiOperation(value = "show dishes that can be made from incoming products")
    public Set<Dish> getDishesByProducts(@RequestBody Set<Product> products) {
        Collection<Dish> dishes = dishRepository.findAll();
        return dishService.getDishesByProducts(dishes, products);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error: " + e.getMessage();
    }
}
