package com.project.talent1.Models;

import javax.persistence.*;

@Entity
public class Talents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long matches;

    public Talents() {

    }

    public Talents(String name, Long matches) {
        this.name = name;
        this.matches = matches;
    }

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
}
