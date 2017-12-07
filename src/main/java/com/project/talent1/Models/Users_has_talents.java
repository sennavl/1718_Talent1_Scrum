package com.project.talent1.Models;

public class Users_has_talents {
  private Long user_id;
  private Long talent_id;
  private String description;
  private String hide;

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

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
