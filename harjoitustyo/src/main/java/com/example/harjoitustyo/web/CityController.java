package com.example.harjoitustyo.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CityController {

    private CityRepository cRepository;
    private LocationRepository lRepository;

    public CityController(CityRepository cRepository, LocationRepository lRepository) {
        this.cRepository = cRepository;
        this.lRepository = lRepository;
    }

    @GetMapping(value = { "/city/{id}" })
    public String getCity(@PathVariable("id") Long cityId, Model model, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        Optional<City> city = cRepository.findById(cityId);
        if (!city.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "City by the id of " + cityId + " does not exist.");
            return "redirect:" + referer;
        }
        Region region = city.get().getRegion();
        model.addAttribute("city", city.get());
        model.addAttribute("region", region);
        model.addAttribute("locations", lRepository.findByCity(city.get()));
        return "city";
    }

    /*
     * @GetMapping(value = { "/region/{id}" })
     * public String getRegion(@PathVariable("id") Long regionId, Model model,
     * RedirectAttributes redirectAttributes, HttpServletRequest request) {
     * String referer = request.getHeader("Referer");
     * Optional<Region> region = rRepository.findById(regionId);
     * if(!region.isPresent()) {
     * redirectAttributes.addFlashAttribute("errorMessage",
     * "Region by the id of " + regionId + " does not exist.");
     * return "redirect:" + referer;
     * }
     * model.addAttribute("region", region.get());
     * model.addAttribute("cities", cRepository.findByRegion(region.get()));
     * return "region";
     * 
     * }
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/city/delete/{id}")
    public String deleteCity(@PathVariable("id") Long cityId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        cRepository.deleteById(cityId);
        return "redirect:" + referer;
    }

}