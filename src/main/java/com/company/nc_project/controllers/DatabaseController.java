package com.company.nc_project.controllers;

import com.company.nc_project.exceptions.ResourceNotFoundException;
import com.company.nc_project.model.Country;
import com.company.nc_project.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
public class DatabaseController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }

}
