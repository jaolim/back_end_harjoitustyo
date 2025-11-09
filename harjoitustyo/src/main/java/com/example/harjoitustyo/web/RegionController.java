package com.example.harjoitustyo.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegionController {

    private RegionRepository rRepository;
    private CityRepository cRepository;

    public RegionController(RegionRepository rRepository, CityRepository cRepository) {
        this.rRepository = rRepository;
        this.cRepository = cRepository;
    }

    // @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = { "/region/{id}" })
    public String getRegion(@PathVariable("id") Long regionId, Model model) {
        Region region = rRepository.findById(regionId)
                .orElseThrow(() -> new CustomNotFoundException("Region by the id of " + regionId + " does not exist."));
        model.addAttribute("region", region);
        model.addAttribute("cities", cRepository.findByRegion(region));
        return "region";

    }

    @GetMapping(value = "/region/delete/{id}")
    public String deleteRegion(@PathVariable("id") Long regionId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        rRepository.deleteById(regionId);
        return "redirect:" + referer;
    }

}