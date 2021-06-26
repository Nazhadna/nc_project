package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
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
    public String deleteClient(@PathVariable(value = "id") UUID clientId) throws EntityNotFoundException {

        clientRepository.deleteById(clientId);

        return "Client with id " + clientId + " deleted";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e) {
        return "error: " + e.getMessage();
    }
}
