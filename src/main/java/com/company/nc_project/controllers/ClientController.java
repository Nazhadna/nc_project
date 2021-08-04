package com.company.nc_project.controllers;

import com.company.nc_project.config.SecurityConfig;
import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.service.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    ClientService clientService;

    @GetMapping()
    @ApiOperation(value = "show all clients")
    @PreAuthorize("hasAuthority('for_admin')")
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get client by id")
    @PreAuthorize("hasAuthority('for_admin')")
    public Optional<Client> getClientById(@PathVariable(value = "id") UUID clientId) {
        return clientRepository.findById(clientId);
    }

    @GetMapping("/who_am_i")
    @ApiOperation(value = "show client")
    @PreAuthorize("hasAuthority('all')")
    public Client getClient(HttpServletRequest request) {
        return clientService.getClientFromRequest(request);
    }

    @PostMapping("/update")
    @ApiOperation(value = "update client")
    @PreAuthorize("hasAuthority('all')")
    public Client updateClient(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PostMapping("/create")
    @ApiOperation(value = "create client")
    public Client createClient(@Valid @RequestBody Client client) {
            client.setPassword(securityConfig.passwordEncoder().encode(client.getPassword()));
            return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete client by id")
    @PreAuthorize("hasAuthority('for_admin')")
    public void deleteClientById(@PathVariable(value = "id") UUID clientId) {
        clientRepository.deleteById(clientId);
    }

    @DeleteMapping("/delete_me")
    @ApiOperation(value = "delete client")
    @PreAuthorize("hasAuthority('for_client')")
    public void deleteClient(HttpServletRequest request) {
        UUID clientId = clientService.getClientFromRequest(request).getId();
        clientRepository.deleteById(clientId);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error: " + e.getMessage();
    }
}
