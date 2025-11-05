package com.example.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.harjoitustyo.domain.Region;


@Controller
public class HomeController {

    public static Region testRegion = new Region("Test Region");
    public static String testing = "asdf";

    @GetMapping(value = { "/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("testRegion", testRegion);
        return "index";
    }

}
