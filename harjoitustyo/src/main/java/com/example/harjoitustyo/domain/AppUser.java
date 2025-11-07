package com.example.harjoitustyo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    private String passwordHash;

    private String firstname;

    private String lastname;

    private String userRole;

    public AppUser() {

    }

    public AppUser(String username, String passwordHash, String firstname, String lastname, String userRole) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstname = firstname;
        this.lastname = lastname;
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
    

    @Override
    public String toString() {
        return "Username: " + username + ", Id: " + appUserId + ", Firstname: " + firstname + ", Lastname: " + lastname + ", PasswordHash: " + passwordHash + ", Role: " + userRole;
    }

}
