package com.example.harjoitustyo.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;

import jakarta.servlet.http.HttpServletRequest;

import com.example.harjoitustyo.domain.CommentRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocationController {

    private CityRepository cRepository;
    private LocationRepository lRepository;
    private CommentRepository coRepository;

    public LocationController(CityRepository cRepository, LocationRepository lRepository,
            CommentRepository coRepository) {
        this.cRepository = cRepository;
        this.lRepository = lRepository;
        this.coRepository = coRepository;
    }

    @GetMapping(value = { "/location/{id}" })
    public String showLocation(@PathVariable("id") Long locationId, Model model, RedirectAttributes redirectAttributes,
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
        model.addAttribute("location", location.get());
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/location/save/{id}")
    public String saveEditedLocation(@PathVariable Long id, Location newLocation,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (newLocation.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location name is required.");
            return "redirect:" + referer;
        }
        if (!cRepository.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the id of " + id + " does not exist");
            return "redirect:/";
        }
        Optional<Location> isSame = lRepository.findByName(newLocation.getName());
        if (isSame.isPresent()) {
            if (isSame.get().getLocationId() != newLocation.getLocationId()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Location the name of " + newLocation.getName() + " already exists.");
                return "redirect:" + referer;
            }
        }
        lRepository.findById(id)
                .map(location -> {
                    location.setName(newLocation.getName());
                    location.setDescription(newLocation.getDescription());
                    location.setImage(newLocation.getImage());
                    return lRepository.save(location);
                });

        return "redirect:/city/" + newLocation.getCity().getCityId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/location/delete/{id}")
    public String removeLocation(@PathVariable("id") Long locationId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        lRepository.deleteById(locationId);
        return "redirect:" + referer;

    }

}
