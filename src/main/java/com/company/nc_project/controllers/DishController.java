package com.company.nc_project.controllers;

import com.company.nc_project.filter.Filter;
import com.company.nc_project.model.*;
import com.company.nc_project.repository.ClientsDishRepository;
import com.company.nc_project.repository.DishRepository;
import com.company.nc_project.repository.StoredProductRepository;
import com.company.nc_project.service.ClientService;
import com.company.nc_project.service.DishService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
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

    @Autowired
    StoredProductRepository storedProductRepository;

    @Autowired
    DishService dishService;

    @Autowired
    ClientService clientService;

    @GetMapping()
    @ApiOperation(value = "show all dishes")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @PostMapping()
    @ApiOperation(value = "create dish")
    @PreAuthorize("hasAuthority('for_admin')")
    public Dish createDish(@Valid @RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get dish by id")
    @PreAuthorize("hasAuthority('all')")
    public Optional<Dish> getDish(@PathVariable(value = "id") UUID dishId) {
        return dishRepository.findById(dishId);
    }

    @PostMapping("/update")
    @ApiOperation(value = "update dish")
    @PreAuthorize("hasAuthority('for_admin')")
    public Dish updateDish(@Valid @RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete dish")
    @PreAuthorize("hasAuthority('for_admin')")
    public void deleteDish(@PathVariable(value = "id") UUID dishId) {
        dishRepository.deleteById(dishId);
    }

    @GetMapping("/{id}/products")
    @ApiOperation(value = "show products from dish")
    @PreAuthorize("hasAuthority('all')")
    public Set<Product> getProductsByDish(@PathVariable(value = "id") UUID dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"));
        return dish.getProducts();
    }

    @PostMapping("/country")
    @ApiOperation(value = "show dishes by country")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Dish> getAllDishesByCountry(@RequestBody Country country) {
        return dishRepository.findAllByCountry(country);
    }

    @PostMapping("/filter")
    @ApiOperation(value = "show dishes by filter")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Dish> getAllDishesByFilter(@RequestBody Filter filter) {
        return dishRepository.findAllByFilter(filter);
    }

    @PostMapping("/client")
    @ApiOperation(value = "show client's dishes")
    @PreAuthorize("hasAuthority('all')")
    public Collection<Dish> getSavedDishesByClient(HttpServletRequest request) {
        Client client = clientService.getClientFromRequest(request);
        return dishRepository.getAllByClientContaining(client);
    }

    @PostMapping("/{dish_id}/client")
    @ApiOperation(value = "save dish for client")
    @PreAuthorize("hasAuthority('all')")
    public void saveDishForClient(@PathVariable(value = "dish_id") UUID dishId, HttpServletRequest request) {
        Client client = clientService.getClientFromRequest(request);
        clientsDishRepository.save(new ClientsDish(client, dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException("No such dish"))));
    }

    @PostMapping("/client_available_dishes/{all_dishes?}")
    @ApiOperation(value = "show client's (or all) dishes that client can cook from his products")
    @PreAuthorize("hasAuthority('all')")
    public Set<Dish> getAvailableDishesByClient(HttpServletRequest request,
                                                @PathVariable(value = "all_dishes?") boolean allDishes) {
        Client client = clientService.getClientFromRequest(request);
        Set<StoredProduct> storedProducts = storedProductRepository.getAllByClient(client);
        return (allDishes) ? dishService.getAvailableDishes(dishRepository.findAll(), storedProducts)
                : dishService.getAvailableDishes(dishRepository.getAllByClientContaining(client), storedProducts);
    }

    @PostMapping("/by_products")
    @ApiOperation(value = "show dishes that can be made from incoming products")
    @PreAuthorize("hasAuthority('all')")
    public Set<Dish> getDishesByProducts(@RequestBody Set<Product> products) {
        Collection<Dish> dishes = dishRepository.findAll();
        return dishService.getDishesByProducts(dishes, products);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error: " + e.getMessage();
    }
}
