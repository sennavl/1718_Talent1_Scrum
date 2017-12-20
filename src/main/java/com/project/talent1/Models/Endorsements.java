package com.project.talent1.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endorsements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Long persons_id;
    private Long users_has_talents_person_id;
    private Long users_has_talents_talent_id;

    public Endorsements() {

    }

    public Endorsements(String description, long persons_id, long users_has_talents_person_id) {
        this.description = description;
        this.persons_id = persons_id;
        this.users_has_talents_person_id = users_has_talents_person_id;
    }

    public Endorsements(String description, long persons_id, long users_has_talents_person_id, long users_has_talents_talent_id) {
        this.description = description;
        this.persons_id = persons_id;
        this.users_has_talents_person_id = users_has_talents_person_id;
        this.users_has_talents_talent_id = users_has_talents_talent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersons_id() {
        return persons_id;
    }

    public void setPersons_id(Long persons_id) {
        this.persons_id = persons_id;
    }

    public Long getUsers_has_talents_person_id() {
        return users_has_talents_person_id;
    }

    public void setUsers_has_talents_person_id(Long users_has_talents_person_id) {
        this.users_has_talents_person_id = users_has_talents_person_id;
    }

    public Long getUsers_has_talents_talent_id() {
        return users_has_talents_talent_id;
    }

    public void setUsers_has_talents_talent_id(Long users_has_talents_talent_id) {
        this.users_has_talents_talent_id = users_has_talents_talent_id;
    }
}
