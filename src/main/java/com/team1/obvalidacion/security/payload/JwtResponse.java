package com.team1.obvalidacion.security.payload;

import com.team1.obvalidacion.entities.Role;

import java.util.Set;

public class JwtResponse {

    private String token;
    private String name;
    private String surname;
    private Set<Role> roles;

    public JwtResponse() {
    }
    public JwtResponse(String token, String name , String surname, Set<Role> roles) {
        this.token = token;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

