package com.project.talent1.Models;

import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Entity
@Table(name = "users")
public class Users {
    @OneToOne
    @JoinColumn(name = "person_id")
    public Persons person;
    @Id
    private Long person_id;
    private java.sql.Date birthday;
    private String password;

    public Users(String password){
        this.password = password;
    }

    public Users(java.sql.Date birthday, String password) {
        this.birthday = birthday;
        this.password = password;
    }

    public Users(long personId, java.sql.Date birthday, String password) {
        this.person_id = personId;
        this.birthday = birthday;
        this.password = password;
    }

    public Users() {

    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(HttpServletResponse response, String password, HttpServletResponse httpServletResponse) throws IOException {
        try {
            if (!BCrypt.checkpw(password, this.getPassword())) {
                response.sendError(SC_UNAUTHORIZED, "Wrong password");
            } else {
                Cookie userCookie = new Cookie("user", getPerson_id().toString());
                userCookie.setMaxAge(30 * 60);
                response.addCookie(userCookie);
            }
        } catch (NullPointerException e) {
            response.sendError(SC_NOT_FOUND, "Users does not exist");
        }
    }

    public void register(HttpServletResponse response, Persons person, UserRepository users, PersonRepository persons) {
        setPassword(BCrypt.hashpw(getPassword(), BCrypt.gensalt()));
        this.person = person.register(persons);
        setPerson_id(person.getId());
        users.save(this);
    }

    public void updateUser(UserRepository users, PersonRepository persons) {
        Users storedUser = users.findByPerson_id(person.getId());
        setPerson_id(person.getId());
        person.setEmail(storedUser.person.getEmail());
        if (getPassword() == null) {
            setPassword(storedUser.getPassword());
        } else {
            setPassword(BCrypt.hashpw(getPassword(), BCrypt.gensalt()));
        }
        if (getBirthday() == null) setBirthday(storedUser.getBirthday());
        if (person.getFirstname() == null) person.setFirstname(storedUser.person.getFirstname());
        if (person.getLastname() == null) person.setLastname(storedUser.person.getLastname());
        users.save(this);
        persons.save(person);
    }
}

