package com.project.talent1.Models;

public class Votes {
  private Long id;
  private String text;
  private String approved;
  private Long users_has_talents_user_id;
  private Long users_has_talents_talent_id;
  private Long person_id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getApproved() {
    return approved;
  }

  public void setApproved(String approved) {
    this.approved = approved;
  }

  public Long getUsers_has_talents_user_id() {
    return users_has_talents_user_id;
  }

  public void setUsers_has_talents_user_id(Long users_has_talents_user_id) {
    this.users_has_talents_user_id = users_has_talents_user_id;
  }

  public Long getUsers_has_talents_talent_id() {
    return users_has_talents_talent_id;
  }

  public void setUsers_has_talents_talent_id(Long users_has_talents_talent_id) {
    this.users_has_talents_talent_id = users_has_talents_talent_id;
  }

  public Long getPerson_id() {
    return person_id;
  }

  public void setPerson_id(Long person_id) {
    this.person_id = person_id;
  }
}
