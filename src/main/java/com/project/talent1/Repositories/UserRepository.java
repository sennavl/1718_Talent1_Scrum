package com.project.talent1.Repositories;

import com.project.talent1.Models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByPerson_id(long id);
}
