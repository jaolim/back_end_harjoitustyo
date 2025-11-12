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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class City {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @JsonView(Views.Public.class)
    @ManyToOne
    @JoinColumn(name = "regionId")
    @JsonIgnoreProperties("cities")
    private Region region;

    @JsonView(Views.Public.class)
    @JsonIgnoreProperties("city")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "city")
    private List<Location> locations;

    @JsonView(Views.Public.class)
    @NotBlank(message = "City name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @JsonView(Views.Public.class)
    private int population;
    @JsonView(Views.Public.class)
    private double area;
    @JsonView(Views.Public.class)
    private String description, image;

    public City() {

    }

    public City(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public City(String name, int population, double area, String description, Region region) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
        this.region = region;
    }

    public City(String name, int population, double area, String description, Region region, String image) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
        this.image = image;
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Population: " + population + ", Area: " + area + ", Description: " + description
                + ", Image source: " + image + ", Id: " + cityId + ", Region: " + region.getName();
    }

}
