package com.project.talent1.Models;

import org.apache.catalina.User;
import javax.persistence.OneToOne;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Persons {
  @Id
  private Long id;
  private String firstname;
  private String lastname;
  private String email;

  @OneToOne
  private Users user;

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
}
