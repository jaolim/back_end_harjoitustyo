package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.harjoitustyo.Views;
import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.AppUserRepository;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

import com.example.harjoitustyo.domain.AppUser;

@CrossOrigin(originPatterns = "*")
@RestController
public class AppUserRestController {

    private AppUserRepository rRepository;

    public AppUserRestController(AppUserRepository rRepository) {
        this.rRepository = rRepository;
    }

    @JsonView(Views.Elevated.class)
    @GetMapping(value = "/appusers")
    public List<AppUser> getAllAppUsers() {
        return (List<AppUser>) rRepository.findAll();

    }

    @JsonView(Views.Elevated.class)
    @GetMapping(value = "/appusers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<AppUser> getAppUserById(@PathVariable Long id) {
        Optional<AppUser> appUser = rRepository.findById(id);
        if (!appUser.isPresent()) {
            throw new CustomNotFoundException("AppUser does not exist");
        }
        return appUser;
    }

    @JsonView(Views.Elevated.class)
    @PostMapping("/appusers")
    public AppUser postAppUser(@Valid @RequestBody AppUser appUser) {
        if (appUser.getAppUserId() != null) {
            throw new CustomBadRequestException("Do not include appUserId");
        } else if (appUser.getUsername() == null || appUser.getUsername().isEmpty()) {
            throw new CustomBadRequestException("AppUser name cannot be empty");
        } else if (appUser.getUserRole() == null || appUser.getUserRole().isEmpty()) {
            throw new CustomBadRequestException("AppUser role cannot be empty");
        } else if (appUser.getPasswordHash() == null || appUser.getPasswordHash().isEmpty()) {
            throw new CustomBadRequestException("AppUser passwordHash cannot be empty");
        }
        return rRepository.save(appUser);
    }

    @JsonView(Views.Elevated.class)
    @DeleteMapping("/appusers/{id}")
    public void deleteAppUser(@PathVariable Long id) {
        if (!rRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("AppUser by id " + id + " does not exist");
        }
        rRepository.deleteById(id);
    }

    @JsonView(Views.Elevated.class)
    @PutMapping("/appusers/{id}")
    public Optional<AppUser> putAppUser(@RequestBody AppUser newAppUser, @PathVariable Long id) {
        if (!rRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("AppUser by id" + id + " does not exist");
        } else if (newAppUser.getUsername() == null || newAppUser.getUsername().isEmpty()) {
            throw new CustomBadRequestException("AppUser name cannot be empty");
        } else if (newAppUser.getUserRole() == null || newAppUser.getUserRole().isEmpty()) {
            throw new CustomBadRequestException("AppUser role cannot be empty");
        } else if (newAppUser.getPasswordHash() == null || newAppUser.getPasswordHash().isEmpty()) {
            throw new CustomBadRequestException("AppUser passwordHash cannot be empty");
        }

        return rRepository.findById(id)
                .map(appUser -> {
                    appUser.setUsername(newAppUser.getUsername());
                    appUser.setFirstname(newAppUser.getFirstname());
                    appUser.setLastname(newAppUser.getLastname());
                    appUser.setPasswordHash(newAppUser.getPasswordHash());
                    appUser.setUserRole(newAppUser.getUserRole());
                    return rRepository.save(appUser);
                });

    }

}
