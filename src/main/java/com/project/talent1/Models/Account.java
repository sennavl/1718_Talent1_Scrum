package com.project.talent1.Models;

import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Account {
    private Long person_id;
    private java.sql.Date birthday;
    private String password;
    private Persons person;
}
