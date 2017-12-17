package com.project.talent1.Repositories;

import com.project.talent1.Models.Users_has_talents;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersHasTalentsRepository extends CrudRepository<Users_has_talents,Long> {
    Iterable<Users_has_talents> findAllByPersonId(long personId);
    Iterable<Users_has_talents> findAll();

    @Query(value = "SELECT * FROM users_has_talents WHERE person_id = ?1 AND talent_id = ?2", nativeQuery = true)
    Users_has_talents findByPersonIdTalentId(int person_id, int talent_id);
}
