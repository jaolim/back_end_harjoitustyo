package com.example.harjoitustyo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @ManyToOne
    @JoinColumn(name = "regionId")
    @JsonIgnoreProperties("cities")
    private Region region;

    @NotBlank(message = "City name is required")
    private String name;

    private int population;
    private double area;
    private String description, image;

    public City() {

    }

    public City(String name) {
        this.name = name;
    }

    public City(String name, int population, double area, String description) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
    }

    public City(String name, int population, double area, String description, String image) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
        this.image = image;
    }

    public City(String name, int population, double area, String description, Region region) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
        this.region = region;
    }

    public City(String name, int population, double area, String description, String image, Region region) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
        this.image = image;
        this.region = region;
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
                + ", Image source: " + image + ", Id: " + cityId + ", Region: " + region;
    }

}
