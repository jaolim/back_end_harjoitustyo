package com.example.harjoitustyo.domain;

import java.util.List;

import com.example.harjoitustyo.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Region {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    @JsonView(Views.Public.class)
    @JsonIgnoreProperties("region")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval =  true, mappedBy = "region")
    private List<City> cities;

    @Column(nullable = false, unique = true)
    @JsonView(Views.Public.class)
    @NotBlank(message = "Region name is required")
    private String name;

    @JsonView(Views.Public.class)
    private String description, image;

    public Region() {

    }

    public Region(String name) {
        this.name = name;
    }

    public Region(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description + ", Image source: " + image + ", Id: " + regionId;
    }

}
