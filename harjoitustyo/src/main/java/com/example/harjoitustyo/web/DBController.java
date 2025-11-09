package com.example.harjoitustyo.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DBController {

    private RegionRepository rRepository;
    private CityRepository cRepository;
    private LocationRepository lRepository;
    private CommentRepository coRepository;
    private AppUserRepository auRepository;

    public DBController(RegionRepository rRepository, CityRepository cRepository, LocationRepository lRepository,
            CommentRepository coRepository, AppUserRepository auRepository) {

        this.rRepository = rRepository;
        this.cRepository = cRepository;
        this.lRepository = lRepository;
        this.coRepository = coRepository;
        this.auRepository = auRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/db/delete")
    public String deleteDB(Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        coRepository.deleteAll();
        lRepository.deleteAll();
        cRepository.deleteAll();
        rRepository.deleteAll();
        auRepository.deleteAll();

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser appUser1 = new AppUser("admin", encoder.encode("admin"), "adminTest", "userTestLast", "ADMIN");
        auRepository.save(appUser1);

        return "redirect:" + referer;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/db/repopulate")
    public String repopulateDB(Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        coRepository.deleteAll();
        lRepository.deleteAll();
        cRepository.deleteAll();
        rRepository.deleteAll();
        auRepository.deleteAll();

        Region region1 = new Region("Cmd: Test Region 1", "A region of test", "");
        Region region2 = new Region("Cmd: Test Region 2");
        City city1 = new City("Cmd: Test City 1", 210542, 80.55, "A city in need is a city indeed", region1);
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin luctus lorem et erat malesuada efficitur. Praesent convallis accumsan risus, at feugiat diam feugiat ullamcorper. Mauris varius ligula eros, nec mattis dui laoreet vel.";
        City city2 = new City("Cmd: Test City 2", 55004, 65.55, lorem, region1);
        Location location1 = new Location("Cmd: Test Location 1", city1);
        Location location2 = new Location("Cmd: Test Location 2", "A lovely location indeed", city2);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser appUser1 = new AppUser("admin", encoder.encode("admin"), "adminTest", "userTestLast", "ADMIN");
        AppUser appUser2 = new AppUser("user", encoder.encode("user"), "userTest", "userTestLast", "USER");
        Comment comment1 = new Comment("Test headline", "A testful body of text", location1, appUser1);
        Comment comment2 = new Comment("Test headline", "A testful body of text", location2, appUser2);

        rRepository.save(region1);
        rRepository.save(region2);
        cRepository.save(city1);
        cRepository.save(city2);
        lRepository.save(location1);
        lRepository.save(location2);
        auRepository.save(appUser1);
        auRepository.save(appUser2);
        coRepository.save(comment1);
        coRepository.save(comment2);

        return "redirect:" + referer;
    }

}
