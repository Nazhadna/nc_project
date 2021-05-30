package com.company.nc_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NcProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(NcProjectApplication.class, args);

        System.out.println("Hello world!");
    }

}
