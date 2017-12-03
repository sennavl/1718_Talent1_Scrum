package com.project.talent1;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Voter {
  @Id
  private long id;
  private String firstname;
  private String lastname;
  private String email;
  private long usersId;


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


  public long getUsersId() {
    return usersId;
  }

  public void setUsersId(long usersId) {
    this.usersId = usersId;
  }

}
