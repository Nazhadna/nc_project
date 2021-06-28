package com.company.nc_project.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class DatabaseController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }

}
