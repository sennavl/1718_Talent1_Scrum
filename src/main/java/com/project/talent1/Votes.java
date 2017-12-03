package com.project.talent1;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Votes {
  @Id
  private long id;
  private String text;
  private String approved;
  private long usersHasTalentsUserId;
  private long usersHasTalentsTalentId;
  private long voterId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
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


  public long getUsersHasTalentsUserId() {
    return usersHasTalentsUserId;
  }

  public void setUsersHasTalentsUserId(long usersHasTalentsUserId) {
    this.usersHasTalentsUserId = usersHasTalentsUserId;
  }


  public long getUsersHasTalentsTalentId() {
    return usersHasTalentsTalentId;
  }

  public void setUsersHasTalentsTalentId(long usersHasTalentsTalentId) {
    this.usersHasTalentsTalentId = usersHasTalentsTalentId;
  }


  public long getVoterId() {
    return voterId;
  }

  public void setVoterId(long voterId) {
    this.voterId = voterId;
  }

}
