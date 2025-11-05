package com.example.harjoitustyo.domain;

import jakarta.validation.constraints.NotBlank;

public class Region {

    @NotBlank(message = "Name is required")
    private String name;

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

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description + ", Image source: " + image;
    }

}
