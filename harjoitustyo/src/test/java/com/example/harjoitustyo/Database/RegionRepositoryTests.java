package com.example.harjoitustyo.Database;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.HarjoitustyoApplication;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest(classes = HarjoitustyoApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RegionRepositoryTests {


    @Autowired
    private RegionRepository regionRepository;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void cleanup() {
        //regionRepository.deleteAll();
    }

    @Test
    public void shouldSaveRegion() {
        Region region = new Region("testName",
                "testDescription",
                "testImage");

        regionRepository.save(region);

        assertTrue(regionRepository.existsByName("testName"));

    }

    @Test
    public void shouldViolateConstraints() {

        Region region = new Region("",
                "testDescription",
                "testImage");

        assertThrows(ConstraintViolationException.class, () -> {
            regionRepository.save(region);
        });

    }

}

