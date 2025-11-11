package com.example.harjoitustyo.web;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegionController {

    private RegionRepository rRepository;
    private CityRepository cRepository;

    public RegionController(RegionRepository rRepository, CityRepository cRepository) {
        this.rRepository = rRepository;
        this.cRepository = cRepository;
    }

    @GetMapping(value = { "/region/{id}" })
    public String getRegion(@PathVariable("id") Long regionId, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        Optional<Region> region = rRepository.findById(regionId);
        if(!region.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Region by the id of " + regionId + " does not exist.");
            return "redirect:" + referer;
        }
        model.addAttribute("region", region.get());
        model.addAttribute("cities", cRepository.findByRegion(region.get()));
        return "region";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/region/delete/{id}")
    public String deleteRegion(@PathVariable("id") Long regionId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        rRepository.deleteById(regionId);
        return "redirect:" + referer;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = { "/region/add" })
    public String addRegion(Model model) {
        model.addAttribute("region", new Region());
        return "regionAdd";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/region/save")
    public String saveRegion(Region region, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        if (region.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Region name is required.");
            return "redirect:" + referer;
        }
        if (rRepository.existsByName(region.getName())) {
            if (region.getRegionId() != null) {
                Region isSame = rRepository.findByName(region.getName());
                if (isSame.getRegionId() != region.getRegionId()) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Region by the name of " + region.getName() + " already exists.");
                    return "redirect:" + referer;
                }
            }
            if (region.getRegionId() == null) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Region by the name of " + region.getName() + " already exists.");
                return "redirect:" + referer;
            }
        }
        rRepository.save(region);
        return "redirect:..";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/region/edit/{id}")
    public String editRegion(@PathVariable("id") Long regionId, Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        Optional<Region> region = rRepository.findById(regionId);
        if (!region.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Region by the ID of " + regionId + " does not exist.");
            return "redirect:" + referer;
        }
        model.addAttribute("region", region);
        return "regionEdit";
    }

}