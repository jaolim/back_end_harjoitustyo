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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commanedId;

    @ManyToOne
    @JoinColumn(name = "appUserId")
    @JsonIgnoreProperties("comments")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "locationId")
    @JsonIgnoreProperties("comments")
    private Location location;

    @NotBlank(message = "Comment headline is required")
    private String headline;

    @NotBlank(message = "Commend body is required")
    private String body;

    public Comment() {

    }

    public Comment(String headline, String body, Location location, AppUser appUser) {
        this.headline = headline;
        this.body = body;
        this.location = location;
        this.appUser = appUser;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setCommanedId(Long commanedId) {
        this.commanedId = commanedId;
    }

    public Long getCommanedId() {
        return commanedId;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return headline + ": " + body + " @" + location.getName() + " by " + appUser.getUsername();
    }
}
