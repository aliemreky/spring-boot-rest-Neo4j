package com.volaid.volaid.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserModel {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String phoneNumber;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean emailPhoneShow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEmailPhoneShow() {
        return emailPhoneShow;
    }

    public void setEmailPhoneShow(Boolean emailPhoneShow) {
        this.emailPhoneShow = emailPhoneShow;
    }
}
