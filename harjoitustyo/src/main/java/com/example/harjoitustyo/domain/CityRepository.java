package com.example.harjoitustyo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

    Optional<City> findByName(String name);

    List<City> findByRegion(Region region);

    boolean existsByName(String name);

}
