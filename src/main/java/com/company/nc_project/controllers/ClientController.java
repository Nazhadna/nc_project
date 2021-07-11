package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.model.Dish;
import com.company.nc_project.model.Product;
import com.company.nc_project.model.StoredItem;
import com.company.nc_project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping()
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable(value = "id") UUID clientId) {
        return clientRepository.findById(clientId);
    }

    @PostMapping("/update")
    public Client updateClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PostMapping()
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable(value = "id") UUID clientId) {
        clientRepository.deleteById(clientId);
    }

    @PostMapping("/{id}/dishes")
    public Set<Dish> getAllDishesByClient(@PathVariable(value = "id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        return client.getClientsDishes();
    }

    @PostMapping("/{id}/stored_items")
    public Set<StoredItem> getStoredItemsByClient(@PathVariable(value = "id") UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        return client.getClientsStoredItems();
    }

    @PostMapping("/{id}/available_dishes")
    public Set<Dish> getAvailableDishesByClient(@PathVariable(value = "id") UUID clientId) {
        Set<Dish> availableDishes = new HashSet<>();
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        ProductNotFound:
        for (Dish dish: client.getClientsDishes()) {
            ProductFound:
            for (Product product: dish.getProducts()) {
                for (StoredItem storedItem: client.getClientsStoredItems()) {
                    if (product.getId() == storedItem.getProduct().getId())
                        break ProductFound;
                }
                break ProductNotFound;
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
