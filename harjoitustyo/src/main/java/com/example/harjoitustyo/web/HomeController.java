package com.example.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Comment;
import com.example.harjoitustyo.domain.CommentRepository;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;


@Controller
public class HomeController {

    private RegionRepository rRepository;
    private CityRepository cRepository;
    private LocationRepository lRepository;
    private AppUserRepository auRepository;
    private CommentRepository coRepository;

    public HomeController(RegionRepository rRepository, CityRepository cRepository, LocationRepository lRepository, AppUserRepository auRepository, CommentRepository coRepository) {
        this.rRepository = rRepository;
        this.cRepository = cRepository;
        this.lRepository = lRepository;
        this.auRepository = auRepository;
        this.coRepository = coRepository;
    }

    public static Region testRegion = new Region("Test Region");
    public static City testCity = new City ("Test City",  123, 321.55 , "Test description", testRegion);
    public static Location testLocation = new Location("Test Location", testCity);
    public static AppUser testAppUser = new AppUser("tester", "asdf", "Testi", "Testaaja", "ADMIN");
    public static Comment testComment = new Comment("Test headline", "A testful body of text", testLocation, testAppUser);

    @GetMapping(value = { "/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("testCity", testCity);
        model.addAttribute("testRegion", testRegion);
        model.addAttribute("testLocation", testLocation);
        model.addAttribute("testAppUser", testAppUser);
        model.addAttribute("testComment", testComment);
        model.addAttribute("regions", rRepository.findAll());
        model.addAttribute("cities", cRepository.findAll());
        model.addAttribute("locations", lRepository.findAll());
        model.addAttribute("appUsers", auRepository.findAll());
        model.addAttribute("comments", coRepository.findAll());
        return "index";
    }

}
