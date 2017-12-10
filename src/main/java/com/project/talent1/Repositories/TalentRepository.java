package com.project.talent1.Repositories;

import com.project.talent1.Models.Talents;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TalentRepository extends CrudRepository<Talents,Long> {
    Talents findById(long id);

    @Query(nativeQuery = true, value = "SELECT TOP 20 * FROM talents ORDER BY matches DESC")
    List<Talents> findTop20();
}
