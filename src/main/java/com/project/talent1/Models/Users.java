package com.project.talent1.Models;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;

public class Users {
  private Long person_id;
  private java.sql.Date birthday;
  private String password;

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
}
