package com.project.talent1.Models;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;

@Entity
@Table(name = "users")
public class Users {
  @Id
  private Long person_id;
  private java.sql.Date birthday;
  private String password;

  private Set<Users_has_talents> userTalents;

  @OneToOne
  @JoinColumn(name = "person_id")
  public Persons person;

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

  public void login(HttpServletResponse response, String password) throws IOException {
    try {
      if (!BCrypt.checkpw(password, this.getPassword())) {
        response.sendError(SC_CONFLICT, "Wrong password");
      }
    } catch (NullPointerException e) {
      response.sendError(SC_CONFLICT, "Users does not exist");
    }
  }

  @OneToMany(mappedBy = "Users", cascade = CascadeType.ALL, orphanRemoval = true)
  public Set<Users_has_talents> getUserTalents() {
    return userTalents;
  }

  public void setUserTalents(Set<Users_has_talents> userTalents) {
    this.userTalents = userTalents;
  }
}
