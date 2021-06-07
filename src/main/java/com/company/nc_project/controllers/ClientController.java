package com.company.nc_project.controllers;

import com.company.nc_project.exceptions.ResourceNotFoundException;
import com.company.nc_project.model.Client;
import com.company.nc_project.model.Country;
import com.company.nc_project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/client")
    public Iterable<Client> getAllContacts() {
        return clientRepository.findAll();
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getContactById(@PathVariable(value = "id") Integer contactId)
            throws ResourceNotFoundException {
        Client client = clientRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id : " + contactId));
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/client")
    public Client createContact(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewContact (@RequestParam String name, @RequestParam Integer age, @RequestParam Character gender,
                                               @RequestParam String email, @RequestParam String password) {
        Client client = new Client();
        client.setName(name);
        client.setAge(age);
        client.setGender(gender);
        client.setEmail(email);
        client.setPassword(password);
        clientRepository.save(client);
        return "Saved";
    }

}
