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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.harjoitustyo.Views;
import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.fasterxml.jackson.annotation.JsonView;

@CrossOrigin(originPatterns = "*")
@RestController
public class LocationRestController {

    private LocationRepository lRepository;
    private CityRepository cRepository;

    public LocationRestController(LocationRepository lRepository, CityRepository cRepository) {
        this.lRepository = lRepository;
        this.cRepository = cRepository;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/locations")
    public List<Location> getAllCities() {
        return (List<Location>) lRepository.findAll();

    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/locations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Location> getLocation(@PathVariable Long id) {
        Optional<Location> location = lRepository.findById(id);
        if (!location.isPresent()) {
            throw new CustomNotFoundException("Location does not exist");
        }
        return location;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Public.class)
    @PostMapping("/locations")
    public Location postLocation(@RequestBody Location location) {
        if (location.getLocationId() != null) {
            throw new CustomBadRequestException("Do not include locationId");
        } else if (location.getName() == null || location.getName().isEmpty()) {
            throw new CustomBadRequestException("Location name cannot be empty");
        } else if (location.getCity() == null || !cRepository.findById(location.getCity().getCityId()).isPresent()) {
            throw new CustomBadRequestException("Wrong or missing City Id");
        }
        return lRepository.save(location);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Public.class)
    @PutMapping("/locations/{id}")
    public Optional<Location> putLocation(@RequestBody Location newLocation, @PathVariable Long id) {
        if (!lRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Location by id" + id + " does not exist");
        } else if (newLocation.getName() == null || newLocation.getName().isEmpty()) {
            throw new CustomBadRequestException("Location name cannot be empty");
        } else if (newLocation.getCity() == null
                || !cRepository.findById(newLocation.getCity().getCityId()).isPresent()) {
            throw new CustomBadRequestException("Wrong or missing City Id");
        }

        return lRepository.findById(id)
                .map(location -> {
                    location.setName(newLocation.getName());
                    location.setDescription(newLocation.getDescription());
                    location.setImage(newLocation.getImage());
                    location.setCity(newLocation.getCity());
                    location.setAddress(newLocation.getAddress());
                    return lRepository.save(location);
                });

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Public.class)
    @DeleteMapping("/locations/{id}")
    public void deleteLocation(@PathVariable Long id) {
        if (!lRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Location by id " + id + " does not exist");
        }
        lRepository.deleteById(id);
    }

}
