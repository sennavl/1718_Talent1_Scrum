package com.project.talent1.Models;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;

@Entity
public class Users {
  @Id
  private long id;
  private String firstname;
  private String lastname;
  private String email;
  private java.sql.Date birthday;
  private String password;


  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public void login(HttpServletResponse response,String password) throws IOException {
    try{
      if(!BCrypt.checkpw(password,this.getPassword())) {
        response.sendError(SC_CONFLICT, "Wrong password");
      }
    }catch (NullPointerException e){
      response.sendError(SC_CONFLICT,"Users does not exist");
    }
  }
}
