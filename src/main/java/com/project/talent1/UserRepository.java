package com.project.talent1;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long>{
    Users findById(long id);
    Users findByEmail(String email);
}
