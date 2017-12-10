package com.project.talent1.Models;

import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Account {
    @Autowired
    static UserRepository users;


    public Persons person;
    public Users user;

    public static void saveAccount(Persons p,Users u){

    }


}
