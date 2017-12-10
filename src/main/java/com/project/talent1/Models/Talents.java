package com.project.talent1.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Talents {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long matches;

  private List<Users_has_talents> userTalents;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getMatches() {
    return matches;
  }

  public void setMatches(Long matches) {
    this.matches = matches;
  }

  @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Users_has_talents> getUserTalents() {
    return userTalents;
  }

  public void setUserTalents(List<Users_has_talents> userTalents) {
    this.userTalents = userTalents;
  }
}
