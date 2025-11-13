package com.example.harjoitustyo.domain;

import java.util.List;

import com.example.harjoitustyo.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Location {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Location name is required")
    @NotNull(message = "Location name is required")
    private String name;

    @JsonView(Views.Public.class)
    @ManyToOne
    @JoinColumn(name = "cityId")
    @JsonIgnoreProperties("locations")
    private City city;

    @JsonView(Views.Public.class)
    @JsonIgnoreProperties("location")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "location")
    private List<Comment> comments;

    @JsonView(Views.Public.class)
    private String description, image, address;

    public Location() {

    }

    public Location(String name, City city) {
        this.name = name;
        this.city = city;
    }

    public Location(String name, String description, City city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public Location(String name, String description, City city, String address) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.address = address;
    }

        public Location(String name, String description, City city, String address, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.city = city;
        this.address = address;
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description
                + ", Image source: " + image + ", Id: " + locationId + ", city: " + city.getName();
    }

}
