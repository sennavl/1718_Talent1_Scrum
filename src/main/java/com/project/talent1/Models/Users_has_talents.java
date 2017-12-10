package com.project.talent1.Models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Users_has_talents {
  private Users user;
  private Talents talent;
  private String description;
  private String hide;

  @Id
  @ManyToOne
  @JoinColumn(name = "person_id")
  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "talent_id")
  public Talents getPublisher() {
    return talent;
  }

  public void setTalent(Talents talent) {
    this.talent = talent;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getHide() {
    return hide;
  }

  public void setHide(String hide) {
    this.hide = hide;
  }
}
