package com.example.harjoitustyo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long>{

        List<Location> findByCity(City city);
        Optional<Location> findByName(String name);
        boolean existsByName(String name);

}
