package com.project.talent1.Repositories;

import com.project.talent1.Models.Talents;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TalentRepository extends CrudRepository<Talents,Long> {
    Talents findById(long id);

    List<Talents> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM talents ORDER BY matches DESC LIMIT 20;")
    List<Talents> findTop20();

    Talents findByNameContaining(String name);
    Talents findByName(String name);
}
