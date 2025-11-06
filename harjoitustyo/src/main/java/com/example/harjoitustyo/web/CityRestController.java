package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class CityRestController {

    private CityRepository cRepository;

    public CityRestController(CityRepository cRepository) {
        this.cRepository = cRepository;
    }

    @GetMapping(value = "/cities")
    public List<City> getAllCities() {
        return (List<City>) cRepository.findAll();

    }

    @GetMapping(value = "/cities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<City> getCityById(@PathVariable Long id) {
        Optional<City> city = cRepository.findById(id);
        if (!city.isPresent()) {
            throw new CustomNotFoundException("City does not exist");
        }
        return city;
    }

    @PostMapping("/cities")
    public City postCity(@RequestBody City city) {
        if (city.getCityId() != null) {
            throw new CustomBadRequestException("Do not include cityId");
        } else if (city.getName() == null || city.getName().isEmpty()) {
            throw new CustomBadRequestException("City name cannot be empty");
        }
        return cRepository.save(city);
    }

    @DeleteMapping("/cities/{id}")
    public void deleteCity(@PathVariable Long id) {
        if (!cRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("City by id " + id + " does not exist");
        }
        cRepository.deleteById(id);
    }

    @PutMapping("/cities/{id}")
    public Optional<City> putCity(@RequestBody City newCity, @PathVariable Long id) {
        if (!cRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("City by id" + id + " does not exist");
        }
        
        return cRepository.findById(id)
                .map(city -> {
                    city.setName(newCity.getName());
                    city.setPopulation(newCity.getPopulation());
                    city.setArea(newCity.getArea());
                    city.setDescription(newCity.getDescription());
                    city.setImage(newCity.getImage());
                    return cRepository.save(city);
                });
                
                

    }

}
