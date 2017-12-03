package com.project.talent1;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Talents {
  @Id
  private long id;
  private String name;
  private long matches;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getMatches() {
    return matches;
  }

  public void setMatches(long matches) {
    this.matches = matches;
  }

}
