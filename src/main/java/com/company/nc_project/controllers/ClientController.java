package com.company.nc_project.controllers;

import com.company.nc_project.exceptions.ResourceNotFoundException;
import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/client")
    public Iterable<Client> getAllContacts() {
        return clientRepository.findAll();
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getContactById(@PathVariable(value = "id") UUID contactId)
            throws ResourceNotFoundException {
        Client client = clientRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for id : " + contactId));
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/client")
    public Client createContact(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/client/{id}")
    public String deleteContact(@PathVariable(value = "id") UUID clientId)
            throws ResourceNotFoundException {

        //clientRepository.deleteById(clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found for id : " + clientId));

        clientRepository.delete(client);
        return "client " + client.getName() + " deleted";
    }
}
