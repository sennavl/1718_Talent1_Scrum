package com.project.talent1.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "talents")
public class Talents {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long matches;

  private Set<UsersTalents> userTalents;

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

  @OneToMany(mappedBy = "talents", cascade = CascadeType.ALL, orphanRemoval = true)
  public Set<UsersTalents> getUserTalents() {
    return userTalents;
  }

  public void setUserTalents(Set<UsersTalents> userTalents) {
    this.userTalents = userTalents;
  }
}
