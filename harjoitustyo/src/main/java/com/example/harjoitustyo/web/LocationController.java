package com.example.harjoitustyo.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;

import jakarta.servlet.http.HttpServletRequest;

import com.example.harjoitustyo.domain.CommentRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocationController {

    private CityRepository cRepository;
    private LocationRepository lRepository;
    private CommentRepository coRepository;

    public LocationController(CityRepository cRepository, LocationRepository lRepository, CommentRepository coRepository) {
        this.cRepository = cRepository;
        this.lRepository = lRepository;
        this.coRepository = coRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = { "/location/add" })
    public String addLocation(Model model) {
        model.addAttribute("location", new Location());
        model.addAttribute("cities", cRepository.findAll());
        return "locationAdd";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/location/edit/{id}")
    public String editLocation(@PathVariable("id") Long locationId, Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        Optional<Location> location = lRepository.findById(locationId);
        if (!location.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the ID of " + locationId + " does not exist.");
            return "redirect:" + referer;
        }
        model.addAttribute("cities", cRepository.findAll());
        model.addAttribute("location", location);
        return "locationEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/location/save")
    public String saveLocation(Location location, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        if (location.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location name is required.");
            return "redirect:" + referer;
        }
        if (lRepository.existsByName(location.getName())) {
            if (location.getLocationId() != null) {
                City isSame = cRepository.findByName(location.getName()).get();
                if (isSame.getCityId() != location.getLocationId()) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Location by the name of " + location.getName() + " already exists.");
                    return "redirect:" + referer;
                }
            }
            if (location.getLocationId() == null) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Location by the name of " + location.getName() + " already exists.");
                return "redirect:" + referer;
            }
        }
        lRepository.save(location);
        return "redirect:../city/" + location.getCity().getCityId();
    }

    @GetMapping(value = { "/location/{id}" })
    public String getLocation(@PathVariable("id") Long locationId, Model model, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        Optional<Location> location = lRepository.findById(locationId);
        if (!location.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the id of " + locationId + " does not exist.");
            return "redirect:" + referer;
        }
        City city = location.get().getCity();
        Region region = city.getRegion();
        model.addAttribute(location.get());
        model.addAttribute("city", city);
        model.addAttribute("region", region);
        model.addAttribute("comments", coRepository.findByLocation(location.get()));
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
