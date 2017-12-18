package com.project.talent1.Repositories;

import com.project.talent1.Models.Persons;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Persons,Long>{
    Persons findByEmail(String email);
    Persons findById(long id);
}
