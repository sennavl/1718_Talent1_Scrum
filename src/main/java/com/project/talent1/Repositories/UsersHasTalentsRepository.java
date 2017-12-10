package com.project.talent1.Repositories;

import com.project.talent1.Models.Users_has_talents;
import org.springframework.data.repository.CrudRepository;

public interface UsersHasTalentsRepository extends CrudRepository<Users_has_talents,Long> {
    Iterable<Users_has_talents> findAllByPersonId(long personId);
    
}
