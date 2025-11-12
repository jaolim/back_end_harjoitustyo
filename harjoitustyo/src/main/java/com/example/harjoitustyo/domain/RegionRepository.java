package com.example.harjoitustyo.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface RegionRepository extends CrudRepository<Region, Long>{
    Optional<Region> findByName(String name);
    boolean existsByName(String name);
}
