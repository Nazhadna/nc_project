package com.company.nc_project.controllers;

import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping()
    @ApiOperation(value = "show all clients")
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get client by id")
    public Optional<Client> getClientById(@PathVariable(value = "id") UUID clientId) {
        return clientRepository.findById(clientId);
    }

    @PostMapping("/update")
    @ApiOperation(value = "update client")
    public Client updateClient(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PostMapping()
    @ApiOperation(value = "create client")
    public Client createClient(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete client")
    @PreAuthorize("hasAuthority('write')")
    public void deleteClient(@PathVariable(value = "id") UUID clientId) {
        clientRepository.deleteById(clientId);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error: " + e.getMessage();
    }
}
