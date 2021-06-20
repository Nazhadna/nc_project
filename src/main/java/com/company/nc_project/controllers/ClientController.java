package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{id}")
    public Client updateClient(@PathVariable(value = "id") UUID clientId, @RequestBody Client client) {
        client.setId(clientId);
        return clientRepository.save(client);
    }

    @PostMapping()
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable(value = "id") UUID clientId) throws EntityNotFoundException {

        clientRepository.deleteById(clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found for id : " + clientId));

        clientRepository.delete(client);

        return "Client with id " + clientId + " deleted";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException e) {
        return "error: " + e.getMessage();
    }
}
