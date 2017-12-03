package com.project.talent1;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class UsersHasTalents {
  @Id
  private long userId;
  private long talentId;
  private String description;
  private String hide;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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
