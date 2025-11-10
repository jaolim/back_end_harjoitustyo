package com.example.harjoitustyo.domain;

import org.springframework.data.repository.CrudRepository;


public interface RegionRepository extends CrudRepository<Region, Long>{
    Region findByName(String name);
    boolean existsByName(String name);
}
