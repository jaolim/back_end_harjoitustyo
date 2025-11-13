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
import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest(classes = HarjoitustyoApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTests {

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void cleanup() {
        appUserRepository.deleteAll();
    }

    @Test
    public void shouldSaveAppUser() {
        AppUser appUser = new AppUser("testAppUser",
                "testpasswordHash",
                "ADMIN",
                "testFirstname", "testLastname");

        appUserRepository.save(appUser);

        assertTrue(appUserRepository.existsByUsername("testAppUser"));

    }

    @Test
    public void shouldViolateConstraints() {

        AppUser appUser = new AppUser("testAppUser",
                "",
                "ADMIN",
                "testFirstname", "testLastname");

        assertThrows(ConstraintViolationException.class, () -> {
            appUserRepository.save(appUser);
        });

        

    }

}
