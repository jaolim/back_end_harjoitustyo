package com.example.harjoitustyo.domain;

import jakarta.validation.constraints.NotBlank;

public class City {

    @NotBlank(message = "City name is required")
    private String name;

    private int population;
    private double area;
    private String description, image;

    public City (String name) {
        this.name = name;
    }

    public City (String name, int population, double area, String description) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
    }

        public City (String name, int population, double area, String description, String image) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.description = description;
        this.image = image;
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

    @Override
    public String toString() {
        return "Name: " + name + ", Population: " + population + ", Area: " + area + ", Description: " + description + ", Image source: " + image;
    }

}
