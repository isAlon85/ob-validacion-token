package com.team1.obvalidacion.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="ob_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    //@JsonIgnore
    private String password;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String surname;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })

    private Set<Role> roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private FrontId frontId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BackId backId;

    @Column
    private boolean validated;

    @Column
    private boolean rejected;

    @Column
    private boolean restarted;

    public User() {
    }

    public User(String password, String email, String name, String surname) {
        this.password = password;
        this.username = email;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public boolean isRestarted() {
        return restarted;
    }

    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    public FrontId getFrontId() {
        return frontId;
    }

    public void setFrontId(FrontId frontId) {
        this.frontId = frontId;
    }

    public BackId getBackId() {
        return backId;
    }

    public void setBackId(BackId backId) {
        this.backId = backId;
    }
}
