package com.example.harjoitustyo.Database;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class CommentRepositoryTests {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        Region region = new Region("testRegion",
                "testRegionDescription",
                "testRegionImage");
        City city = new City("testCity", 1, 1, region);

        Location location = new Location("testLocation", city);

        AppUser appUser = new AppUser("testAppUser",
                "testpasswordHash",
                "ADMIN",
                "testFirstname", "testLastname");
        regionRepository.save(region);
        cityRepository.save(city);
        locationRepository.save(location);
        appUserRepository.save(appUser);
    }

    @AfterEach
    public void cleanup() {
        commentRepository.deleteAll();
        cityRepository.deleteAll();
        regionRepository.deleteAll();
        locationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    public void shouldSaveComment() {

        Optional<Region> region = regionRepository.findByName("testRegion");
        assertTrue(region.isPresent());

        Optional<City> city = cityRepository.findByName("testCity");
        assertTrue(city.isPresent());

        Optional<Location> location = locationRepository.findByName("testLocation");
        assertTrue(location.isPresent());

        Optional<AppUser> appUser = appUserRepository.findByUsername("testAppUser");
        assertTrue(city.isPresent());

        Comment comment = new Comment("testHeadline", "testBody", location.get(), appUser.get());
        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findByLocation(location.get());
        assertTrue(comments.size() > 0);

        assertTrue(comments.get(0).getLocation().getCity().getRegion().getName().equals("testRegion"));

        assertTrue(comments.get(0).getAppUser().getUsername().equals("testAppUser"));

    }


    @Test
    public void shouldViolateConstraints() {

        Optional<Region> region = regionRepository.findByName("testRegion");
        assertTrue(region.isPresent());

        Optional<City> city = cityRepository.findByName("testCity");
        assertTrue(city.isPresent());

        Optional<Location> location = locationRepository.findByName("testLocation");
        assertTrue(location.isPresent());

        Optional<AppUser> appUser = appUserRepository.findByUsername("testAppUser");
        assertTrue(city.isPresent());

        Comment comment = new Comment("", "testBody", location.get(), appUser.get());
        assertThrows(ConstraintViolationException.class, () -> {
            commentRepository.save(comment);
        });

    }

}
