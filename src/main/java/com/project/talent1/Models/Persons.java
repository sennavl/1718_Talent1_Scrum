package com.project.talent1.Models;

import com.project.talent1.Repositories.PersonRepository;

import javax.persistence.*;

@Entity
public class Persons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    public Persons(long id, String firstname) {
        this.id = id;
        this.firstname = firstname;
    }

    public Persons(String firstname, String email) {
        this.firstname = firstname;
        this.email = email;
    }

    public Persons(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Persons(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Persons() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Persons register(PersonRepository persons) {
        persons.save(this);
        return persons.findByEmail(getEmail());
    }
}
