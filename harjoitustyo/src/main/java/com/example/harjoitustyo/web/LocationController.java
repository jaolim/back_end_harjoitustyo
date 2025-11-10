package com.example.harjoitustyo.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;

import jakarta.servlet.http.HttpServletRequest;

import com.example.harjoitustyo.domain.CommentRepository;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LocationController {

    private LocationRepository lRepository;
    private CommentRepository coRepository;

    public LocationController(LocationRepository lRepository, CommentRepository coRepository) {
        this.lRepository = lRepository;
        this.coRepository = coRepository;
    }

    @GetMapping(value = { "/location/{id}" })
    public String getLocation(@PathVariable("id") Long locationId, Model model) {
        Location location = lRepository.findById(locationId)
                .orElseThrow(() -> new CustomNotFoundException("Location by the id of " + locationId + " does not exist."));
        City city = location.getCity();
        Region region = city.getRegion();
        model.addAttribute(location);
        model.addAttribute("city", city);
        model.addAttribute("region", region);
        model.addAttribute("comments", coRepository.findByLocation(location));
        return "location";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/location/delete/{id}")
    public String deleteLocation(@PathVariable("id") Long locationId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        lRepository.deleteById(locationId);
        return "redirect:" + referer;

    }

    
}

