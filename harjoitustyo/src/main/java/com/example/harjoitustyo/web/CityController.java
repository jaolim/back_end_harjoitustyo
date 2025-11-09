package com.example.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String getCity(@PathVariable("id") Long cityId, Model model) {
        City city = cRepository.findById(cityId)
                .orElseThrow(() -> new CustomNotFoundException("City by the id of " + cityId + " does not exist."));
        Region region = city.getRegion();
        model.addAttribute("city", city);
        model.addAttribute("region", region);
        model.addAttribute("locations", lRepository.findByCity(city));
        return "city";
    }

    @GetMapping(value = "/city/delete/{id}")
    public String deleteCity(@PathVariable("id") Long cityId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        cRepository.deleteById(cityId);
        return "redirect:" + referer;
    }

}