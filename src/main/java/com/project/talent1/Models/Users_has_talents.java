package com.project.talent1.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users_has_talents {

  private Long person_id;

  private Long talent_id;
  private String description;
  private String hide;

  @Id
  public Long getPerson_id() {
    return person_id;
  }

  public void setPerson_id(Long person_id) {
    this.person_id = person_id;
  }

  @Id
  public Long getTalent_id() {
    return talent_id;
  }

  public void setTalent_id(Long talent_id) {
    this.talent_id = talent_id;
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
