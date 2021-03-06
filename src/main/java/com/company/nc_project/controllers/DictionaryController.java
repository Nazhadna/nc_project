package com.company.nc_project.controllers;

import com.company.nc_project.model.*;
import com.company.nc_project.repository.CountryRepository;
import com.company.nc_project.repository.GenderRepository;
import com.company.nc_project.repository.PlaceRepository;
import com.company.nc_project.repository.UnitRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Iterable<Gender> getAllGenders() { return genderRepository.findAll(); }

    @GetMapping("/gender/{id}")
    @ApiOperation(value = "get gender by id")
    public Optional<Gender> getGenderById(@PathVariable(value = "id") UUID genderId) {
        return genderRepository.findById(genderId);
    }

    @PostMapping("/gender")
    @ApiOperation(value = "add gender")
    @PreAuthorize("hasAuthority('for_admin')")
    public Gender addGender(@Valid @RequestBody Gender gender) {
        return genderRepository.save(gender);
    }

    @PostMapping("/gender/update")
    @ApiOperation(value = "update gender")
    @PreAuthorize("hasAuthority('for_admin')")
    public Gender updateGender(@Valid @RequestBody Gender gender) {
        return genderRepository.save(gender);
    }

    @GetMapping("/place")
    @ApiOperation(value = "show all places")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Place> getAllPlaces() { return placeRepository.findAll(); }

    @GetMapping("/place/{id}")
    @ApiOperation(value = "get place by id")
    @PreAuthorize("hasAuthority('all')")
    public Optional<Place> getPlaceById(@PathVariable(value = "id") UUID placeId) {
        return placeRepository.findById(placeId);
    }

    @PostMapping("/place")
    @ApiOperation(value = "add place")
    @PreAuthorize("hasAuthority('for_admin')")
    public Place addPlace(@Valid @RequestBody Place place) {
        return placeRepository.save(place);
    }

    @PostMapping("/place/update")
    @ApiOperation(value = "update place")
    @PreAuthorize("hasAuthority('for_admin')")
    public Place updatePlace(@Valid @RequestBody Place place) {
        return placeRepository.save(place);
    }

    @GetMapping("/unit")
    @ApiOperation(value = "show all units")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Unit> getAllUnits() { return unitRepository.findAll(); }

    @GetMapping("/unit/{id}")
    @ApiOperation(value = "get unit by id")
    @PreAuthorize("hasAuthority('all')")
    public Optional<Unit> getUnitById(@PathVariable(value = "id") UUID unitId) {
        return unitRepository.findById(unitId);
    }

    @PostMapping("/unit")
    @ApiOperation(value = "add unit")
    @PreAuthorize("hasAuthority('for_admin')")
    public Unit addUnit(@Valid @RequestBody Unit unit) {
        return unitRepository.save(unit);
    }

    @PostMapping("/unit/update")
    @ApiOperation(value = "update unit")
    @PreAuthorize("hasAuthority('for_admin')")
    public Unit updateUnit(@Valid @RequestBody Unit unit) {
        return unitRepository.save(unit);
    }

    @GetMapping("/country")
    @ApiOperation(value = "show all countries")
    @PreAuthorize("hasAuthority('all')")
    public Iterable<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/country/{id}")
    @ApiOperation(value = "get country by id")
    @PreAuthorize("hasAuthority('all')")
    public Optional<Country> getCountryById(@PathVariable(value = "id") UUID countryId) {
        return countryRepository.findById(countryId);
    }

    @PostMapping("/country")
    @ApiOperation(value = "add country")
    @PreAuthorize("hasAuthority('for_admin')")
    public Country addCountry(@Valid @RequestBody Country country) {
        return countryRepository.save(country);
    }

    @PostMapping("/country/update")
    @ApiOperation(value = "update country")
    @PreAuthorize("hasAuthority('for_admin')")
    public Country updateCountry(@Valid @RequestBody Country country) {
        return countryRepository.save(country);
    }
}
