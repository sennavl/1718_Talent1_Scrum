package com.project.talent1.Models;

import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Account {
    public int id;
    public String firstName,lastName;
    public String email;
    public java.sql.Date date;
    public String pass;

    @Autowired
    private UserRepository users;
    @Autowired
    private PersonRepository people;

    public Account(Persons p){

    }
}
