package com.example.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;


@Controller
public class HomeController {

    private RegionRepository rRepository;
    private CityRepository cRepository;

    public HomeController(RegionRepository rRepository, CityRepository cRepository) {
        this.rRepository = rRepository;
        this.cRepository = cRepository;
    }

    public static Region testRegion = new Region("Test Region");
    public static City testCity = new City ("Test City",  123, 321.55 , "Test description");

    @GetMapping(value = { "/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("testCity", testCity);
        model.addAttribute("testRegion", testRegion);
        model.addAttribute("regions", rRepository.findAll());
        model.addAttribute("cities", cRepository.findAll());
        return "index";
    }

}
