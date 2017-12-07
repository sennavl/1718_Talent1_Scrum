package com.project.talent1.Repositories;

import com.project.talent1.Models.Talents;
import org.springframework.data.repository.CrudRepository;

public interface TalentRepository extends CrudRepository<Talents,Long> {
    Talents findById(long id);
}
