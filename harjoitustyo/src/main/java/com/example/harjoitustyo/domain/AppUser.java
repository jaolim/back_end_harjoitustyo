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
public class AppUser {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @JsonView(Views.Public.class)
    @JsonIgnoreProperties("appUser")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "appUser")
    private List<Comment> comments;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Username cannot be empty")
    @Column(nullable = false, unique = true)
    private String username;

    @JsonView(Views.Internal.class)
    private String passwordHash;

    @JsonView(Views.Elevated.class)
    private String firstname;

    @JsonView(Views.Elevated.class)
    private String lastname;

    @JsonView(Views.Elevated.class)
    @NotBlank
    private String userRole;

    public AppUser() {

    }

    public AppUser(String username, String passwordHash, String userRole, String firstname, String lastname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public AppUser(String username, String passwordHash, String userRole, String firstname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.firstname = firstname;
    }

    public AppUser(String username, String passwordHash, String userRole) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Id: " + appUserId + ", Firstname: " + firstname + ", Lastname: " + lastname
                + ", PasswordHash: " + passwordHash + ", Role: " + userRole;
    }

}
