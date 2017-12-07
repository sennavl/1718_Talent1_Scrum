package com.project.talent1.Models;

import javax.persistence.Id;

import javax.persistence.Entity;

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

}
