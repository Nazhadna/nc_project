package com.company.nc_project.controllers;

import com.company.nc_project.model.*;
import com.company.nc_project.repository.CountryRepository;
import com.company.nc_project.repository.GenderRepository;
import com.company.nc_project.repository.PlaceRepository;
import com.company.nc_project.repository.UnitRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ApiOperation(value = "show all genders")
    @PreAuthorize("hasAuthority('read')")
    public Iterable<Gender> getAllGenders() { return genderRepository.findAll(); }

    @GetMapping("/gender/{id}")
    @ApiOperation(value = "get gender by id")
    @PreAuthorize("hasAuthority('read')")
    public Optional<Gender> getGenderById(@PathVariable(value = "id") UUID genderId) {
        return genderRepository.findById(genderId);
    }

    @GetMapping("/country")
    @ApiOperation(value = "show all countries")
    @PreAuthorize("hasAuthority('read')")
    public Iterable<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/country/{id}")
    @ApiOperation(value = "get country by id")
    @PreAuthorize("hasAuthority('read')")
    public Optional<Country> getCountryById(@PathVariable(value = "id") UUID countryId) {
        return countryRepository.findById(countryId);
    }

    @GetMapping("/place")
    @ApiOperation(value = "show all places")
    @PreAuthorize("hasAuthority('read')")
    public Iterable<Place> getAllPlaces() { return placeRepository.findAll(); }

    @GetMapping("/place/{id}")
    @ApiOperation(value = "get place by id")
    @PreAuthorize("hasAuthority('read')")
    public Optional<Place> getPlaceById(@PathVariable(value = "id") UUID placeId) {
        return placeRepository.findById(placeId);
    }

    @GetMapping("/unit")
    @ApiOperation(value = "show all units")
    @PreAuthorize("hasAuthority('read')")
    public Iterable<Unit> getAllUnits() { return unitRepository.findAll(); }

    @GetMapping("/unit/{id}")
    @ApiOperation(value = "get unit by id")
    @PreAuthorize("hasAuthority('read')")
    public Optional<Unit> getUnitById(@PathVariable(value = "id") UUID unitId) {
        return unitRepository.findById(unitId);
    }
}
