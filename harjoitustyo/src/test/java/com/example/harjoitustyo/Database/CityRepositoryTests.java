package com.example.harjoitustyo.Database;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class CityRepositoryTests {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @BeforeEach
    public void setup() {
        Region region = new Region("testRegion",
                "testDescription",
                "testImage");

        regionRepository.save(region);
    }

    @AfterEach
    public void cleanup() {
        cityRepository.deleteAll();
        regionRepository.deleteAll();
    }

    @Test
    public void shouldSaveCity() {

        Optional<Region> region = regionRepository.findByName("testRegion");

        assertTrue(region.isPresent());

        City city = new City("testCity", 1, 1,region.get());

        cityRepository.save(city);

        Optional<City> cityFetch = cityRepository.findByName("testCity");

        assertTrue(cityFetch.isPresent());

        assertTrue(cityFetch.get().getRegion().getName().equals("testRegion"));

    }

    @Test
    public void shouldViolateConstraints() {

        Optional<Region> region = regionRepository.findByName("testRegion");


        City city = new City("testCity", 0, 1, region.get());


        assertThrows(ConstraintViolationException.class, () -> {
            cityRepository.save(city);
        });
        
    }
}
