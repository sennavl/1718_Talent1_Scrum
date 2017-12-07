package com.project.talent1;

import com.project.talent1.Models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long>{
    Users findById(long id);
    Users findByEmail(String email);
}
