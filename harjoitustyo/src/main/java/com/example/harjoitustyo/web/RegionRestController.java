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
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin(originPatterns = "*")
@RestController
public class RegionRestController {

    private RegionRepository rRepository;

    public RegionRestController(RegionRepository rRepository) {
        this.rRepository = rRepository;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/regions")
    public List<Region> getAllRegions() {
        return (List<Region>) rRepository.findAll();

    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/regions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Region> getRegion(@PathVariable Long id) {
        Optional<Region> region = rRepository.findById(id);
        if (!region.isPresent()) {
            throw new CustomNotFoundException("Region does not exist");
        }
        return region;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Elevated.class)
    @PostMapping("/regions")
    public Region postRegion(@Valid @RequestBody Region region) {
        if (region.getRegionId() != null) {
            throw new CustomBadRequestException("Do not include regionId");
        } else if (region.getName() == null || region.getName().isEmpty()) {
            throw new CustomBadRequestException("Region name cannot be empty");
        }
        Optional<Region> isSame = rRepository.findByName(region.getName());
        if (isSame.isPresent()) {
            throw new CustomBadRequestException("Region name has to be unique");
        }
        return rRepository.save(region);
    }

    @JsonView(Views.Elevated.class)
    @PutMapping("/regions/{id}")
    public Optional<Region> putRegion(@Valid @RequestBody Region newRegion, @PathVariable Long id) {
        if (!rRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Region by the id of " + id + " does not exist");
        } else if (newRegion.getName() == null || newRegion.getName().isEmpty()) {
            throw new CustomBadRequestException("Region name cannot be empty");
        }
        Optional<Region> isSame = rRepository.findByName(newRegion.getName());
        if (isSame.isPresent() && isSame.get().getRegionId() != id) {
            throw new CustomBadRequestException("Region name has to be unique");
        }

        return rRepository.findById(id)
                .map(region -> {
                    region.setName(newRegion.getName());
                    region.setDescription(newRegion.getDescription());
                    region.setImage(newRegion.getImage());
                    return rRepository.save(region);
                });

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.Elevated.class)
    @DeleteMapping("/regions/{id}")
    public void deleteRegion(@PathVariable Long id) {
        if (!rRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Region by the id of " + id + " does not exist");
        }
        rRepository.deleteById(id);
    }

}
