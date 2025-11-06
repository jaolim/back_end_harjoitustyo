package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class RegionRestController {

    private RegionRepository rRepository;

    public RegionRestController(RegionRepository rRepository) {
        this.rRepository = rRepository;
    }

    @GetMapping(value = "/regions")
    public List<Region> getAllRegions() {
        return (List<Region>) rRepository.findAll();

    }

    @GetMapping(value = "/regions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Region> getRegionById(@PathVariable Long id) {
        Optional<Region> region = rRepository.findById(id);
        if (!region.isPresent()) {
            throw new CustomNotFoundException("Region does not exist");
        }
        return region;
    }

}
