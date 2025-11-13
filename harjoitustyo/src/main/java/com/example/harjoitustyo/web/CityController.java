package com.example.harjoitustyo.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CityController {

    private RegionRepository rRepository;
    private CityRepository cRepository;
    private LocationRepository lRepository;

    public CityController(RegionRepository rRepository, CityRepository cRepository, LocationRepository lRepository) {
        this.cRepository = cRepository;
        this.lRepository = lRepository;
        this.rRepository = rRepository;
    }

    @GetMapping(value = { "/city/{id}" })
    public String showCity(@PathVariable("id") Long cityId, Model model, RedirectAttributes redirectAttributes,
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = { "/city/add" })
    public String addCity(Model model) {
        model.addAttribute("city", new City());
        model.addAttribute("regions", rRepository.findAll());
        return "cityAdd";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/city/edit/{id}")
    public String editCity(@PathVariable("id") Long cityId, Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        Optional<City> city = cRepository.findById(cityId);
        if (!city.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "City by the ID of " + cityId + " does not exist.");
            return "redirect:" + referer;
        }
        model.addAttribute("regions", rRepository.findAll());
        model.addAttribute("city", city.get());
        return "cityEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/city/save")
    public String saveCity(City city, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        if (city.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "City name is required.");
            return "redirect:" + referer;
        }
        if (cRepository.existsByName(city.getName())) {
            if (city.getCityId() != null) {
                City isSame = cRepository.findByName(city.getName()).get();
                if (isSame.getCityId() != city.getCityId()) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "City by the name of " + city.getName() + " already exists.");
                    return "redirect:" + referer;
                }
            }
            if (city.getCityId() == null) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "City by the name of " + city.getName() + " already exists.");
                return "redirect:" + referer;
            }
        }
        try {
            if (city.getPopulation() <= 0 || city.getArea() <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Population has to be a positive integer and Area has to be a positive number");
                return "redirect:" + referer;
            }
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Population has to be a positive integer and Area has to be a positive number");
            return "redirect:" + referer;
        }
        cRepository.save(city);
        return "redirect:../region/" + city.getRegion().getRegionId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/city/save/{id}")
    public String saveEditedCity(@PathVariable Long id, City newCity,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (newCity.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "City name is required.");
            return "redirect:" + referer;
        }
        if (!cRepository.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "City by the id of " + id + " does not exist");
            return "redirect:/";
        }
        Optional<City> isSame = cRepository.findByName(newCity.getName());
        if (isSame.isPresent()) {
            if (isSame.get().getCityId() != newCity.getCityId()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "City the name of " + newCity.getName() + " already exists.");
                return "redirect:" + referer;
            }
        }
        try {
            if (newCity.getPopulation() <= 0 || newCity.getArea() <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Population has to be a positive integer and Area has to be a positive number");
                return "redirect:" + referer;
            }
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Population has to be a positive integer and Area has to be a positive number");
            return "redirect:" + referer;

        }
        cRepository.findById(id)
                .map(city -> {
                    city.setName(newCity.getName());
                    city.setDescription(newCity.getDescription());
                    city.setImage(newCity.getImage());
                    return cRepository.save(city);
                });

        return "redirect:/region/" + newCity.getRegion().getRegionId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/city/delete/{id}")
    public String removeCity(@PathVariable("id") Long cityId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        cRepository.deleteById(cityId);
        return "redirect:" + referer;
    }

}