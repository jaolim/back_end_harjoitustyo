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
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @NotBlank(message = "Region name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cityId")
    @JsonIgnoreProperties("locations")
    private City city;

    private String description, image;

    public Location() {

    }

    public Location(String name, City city){
        this.name = name;
        this.city = city;
    }
        public Location(String name, String description, City city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public Location(String name, String description, City city, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

        @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description
                + ", Image source: " + image + ", Id: " + locationId + ", city: " + city.getName();
    }


}
