package com.company.nc_project.controllers;

import com.company.nc_project.model.*;
import com.company.nc_project.repository.CountryRepository;
import com.company.nc_project.repository.GenderRepository;
import com.company.nc_project.repository.PlaceRepository;
import com.company.nc_project.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    @Autowired
    GenderRepository genderRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    UnitRepository unitRepository;

    @GetMapping("/gender")
    public Iterable<Gender> getAllGenders() { return genderRepository.findAll(); }

    @GetMapping("/gender/{id}")
    public Optional<Gender> getGenderById(@PathVariable(value = "id") UUID genderId) {
        return genderRepository.findById(genderId);
    }

    @GetMapping("/country")
    public Iterable<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/country/{id}")
    public Optional<Country> getCountryById(@PathVariable(value = "id") UUID countryId) {
        return countryRepository.findById(countryId);
    }

    @GetMapping("/place")
    public Iterable<Place> getAllPlaces() { return placeRepository.findAll(); }

    @GetMapping("/place/{id}")
    public Optional<Place> getPlaceById(@PathVariable(value = "id") UUID placeId) {
        return placeRepository.findById(placeId);
    }

    @GetMapping("/unit")
    public Iterable<Unit> getAllUnits() { return unitRepository.findAll(); }

    @GetMapping("/unit/{id}")
    public Optional<Unit> getUnitById(@PathVariable(value = "id") UUID unitId) {
        return unitRepository.findById(unitId);
    }
}
