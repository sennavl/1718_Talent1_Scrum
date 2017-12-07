package com.project.talent1.Models;

public class Users {
  private Long id;
  private java.sql.Date birthday;
  private String password;
  private Long person_id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getPerson_id() {
    return person_id;
  }

  public void setPerson_id(Long person_id) {
    this.person_id = person_id;
  }
}
