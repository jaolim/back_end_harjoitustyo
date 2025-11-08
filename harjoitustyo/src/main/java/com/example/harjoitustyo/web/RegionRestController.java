package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class RegionRestController {

    private RegionRepository rRepository;

    public RegionRestController(RegionRepository rRepository) {
        this.rRepository = rRepository;
    }

    @JsonView(Views.Public.class)
    @GetMapping(value = "/regions")
    public List<Region> getAllRegions() {
        return (List<Region>) rRepository.findAll();

    }

    @JsonView(Views.Public.class)
    @GetMapping(value = "/regions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Region> getRegionById(@PathVariable Long id) {
        Optional<Region> region = rRepository.findById(id);
        if (!region.isPresent()) {
            throw new CustomNotFoundException("Region does not exist");
        }
        return region;
    }

    @JsonView(Views.Elevated.class)
    @PostMapping("/regions")
    public Region postRegion(@RequestBody Region region) {
        if (region.getRegionId() != null) {
            throw new CustomBadRequestException("Do not include regionId");
        } else if (region.getName() == null || region.getName().isEmpty()) {
            throw new CustomBadRequestException("Region name cannot be empty");
        }
        return rRepository.save(region);
    }

    @JsonView(Views.Elevated.class)
    @DeleteMapping("/regions/{id}")
    public void deleteRegion(@PathVariable Long id) {
        if (!rRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Region by id " + id + " does not exist");
        }
        rRepository.deleteById(id);
    }

    @JsonView(Views.Elevated.class)
    @PutMapping("/regions/{id}")
    public Optional<Region> putRegion(@RequestBody Region newRegion, @PathVariable Long id) {
        if (!rRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Region by id" + id + " does not exist");
        } else if (newRegion.getName() == null || newRegion.getName().isEmpty()) {
            throw new CustomBadRequestException("Region name cannot be empty");
        }

        return rRepository.findById(id)
                .map(region -> {
                    region.setName(newRegion.getName());
                    region.setDescription(newRegion.getDescription());
                    region.setImage(newRegion.getImage());
                    return rRepository.save(region);
                });

    }

}
