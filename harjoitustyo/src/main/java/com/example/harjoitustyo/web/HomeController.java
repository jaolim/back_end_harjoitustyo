package com.example.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.harjoitustyo.domain.RegionRepository;


@Controller
public class HomeController {

    private RegionRepository rRepository;

    public HomeController(RegionRepository rRepository) {
        this.rRepository = rRepository;
    }

    @GetMapping(value = { "/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("regions", rRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

}
