package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.harjoitustyo.Views;
import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.RegionRepository;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin(originPatterns = "*")
@RestController
public class CityRestController {

    private CityRepository cRepository;
    private RegionRepository rRepository;

    public CityRestController(CityRepository cRepository, RegionRepository rRepository) {
        this.cRepository = cRepository;
        this.rRepository = rRepository;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/cities")
    public List<City> getAllCities() {
        return (List<City>) cRepository.findAll();

    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/cities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<City> getCity(@PathVariable Long id) {
        Optional<City> city = cRepository.findById(id);
        if (!city.isPresent()) {
            throw new CustomNotFoundException("City does not exist");
        }
        return city;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Elevated.class)
    @PostMapping("/cities")
    public City postCity(@Valid @RequestBody City city) {
        if (city.getCityId() != null) {
            throw new CustomBadRequestException("Do not include cityId");
        }

        Optional<City> isSame = cRepository.findByName(city.getName());
        if (isSame.isPresent()) {
            throw new CustomBadRequestException("City name has to be unique");
        }

        // Obsolete manual validation, now handled by @Valid implementation instead
        /*
         * if (city.getName() == null || city.getName().isEmpty()) {
         * throw new CustomBadRequestException("City name cannot be empty");
         * } else if (city.getRegion() == null ||
         * !rRepository.findById(city.getRegion().getRegionId()).isPresent()) {
         * throw new CustomBadRequestException("Wrong or missing Region Id");
         * }
         */
        return cRepository.save(city);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Elevated.class)
    @PutMapping("/cities/{id}")
    public Optional<City> putCity(@Valid @RequestBody City newCity, @PathVariable Long id) {
        if (!cRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("City by the id of " + id + " does not exist");
        }

        Optional<City> isSame = cRepository.findByName(newCity.getName());
        if (isSame.isPresent() && isSame.get().getCityId() != id) {
            throw new CustomBadRequestException("City name has to be unique");
        }

        // Obsolete manual validation, now handled by @Valid implementation instead
        /*
         * if (newCity.getName() == null || newCity.getName().isEmpty()) {
         * throw new CustomBadRequestException("City name cannot be empty");
         * } else if (newCity.getRegion() == null
         * || !rRepository.findById(newCity.getRegion().getRegionId()).isPresent()) {
         * throw new CustomBadRequestException("Wrong or missing Region Id");
         * }
         */

        return cRepository.findById(id)
                .map(city -> {
                    city.setName(newCity.getName());
                    city.setPopulation(newCity.getPopulation());
                    city.setArea(newCity.getArea());
                    city.setDescription(newCity.getDescription());
                    city.setImage(newCity.getImage());
                    city.setRegion(newCity.getRegion());
                    return cRepository.save(city);
                });

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Elevated.class)
    @DeleteMapping("/cities/{id}")
    public void deleteCity(@PathVariable Long id) {
        if (!cRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("City by the id of " + id + " does not exist");
        }
        cRepository.deleteById(id);
    }

}
