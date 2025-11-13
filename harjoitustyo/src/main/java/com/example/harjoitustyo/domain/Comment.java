package com.example.harjoitustyo.domain;

import com.example.harjoitustyo.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Comment {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @JsonView(Views.Public.class)
    @ManyToOne
    @JoinColumn(name = "appUserId")
    @JsonIgnoreProperties("comments")
    private AppUser appUser;

    @JsonView(Views.Public.class)
    @ManyToOne
    @JoinColumn(name = "locationId")
    @JsonIgnoreProperties("comments")
    private Location location;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Comment headline is required")
    @NotNull
    @Size(max = 30)
    private String headline;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Comment body is required")
    @NotNull(message = "Comment body is required")
    @Size(max = 500)
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

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
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
