package com.project.talent1.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users_has_talents {
  @Id
  private long personId;
  private long talentId;
  private String description;
  private String hide;


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }


  public long getTalentId() {
    return talentId;
  }

  public void setTalentId(long talentId) {
    this.talentId = talentId;
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
