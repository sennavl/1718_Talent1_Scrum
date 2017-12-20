package com.project.talent1.Models;

import com.project.talent1.Repositories.UsersHasTalentsRepository;
import com.project.talent1.Repositories.VotesRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Votes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private int approved;
    private Long person_id;
    private Long users_has_talents_person_id;
    private Long users_has_talents_talent_id;

    public Votes() {

    }

    public Votes(String text, long person_id, long users_has_talents_person_id, long users_has_talents_talent_id) {
        this.text = text;
        this.person_id = person_id;
        this.users_has_talents_person_id = users_has_talents_person_id;
        this.users_has_talents_talent_id = users_has_talents_talent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
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

    public void AcceptVote(VotesRepository votesRepo, UsersHasTalentsRepository userTalentRepo, boolean hide) {
        Users_has_talents userTalent = new Users_has_talents();
        userTalent.setTalentId(getUsers_has_talents_talent_id());
        userTalent.setDescription(getText());
        userTalent.setHide((hide) ? 1 : 0);
        userTalent.setPersonId(getUsers_has_talents_person_id());
        userTalentRepo.save(userTalent);
        votesRepo.delete(getId());
    }

    public void RefuseVote(VotesRepository votesRepo) {
        votesRepo.delete(getId());
    }
}
