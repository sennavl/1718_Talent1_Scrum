package com.project.talent1.Repositories;

import com.project.talent1.Models.Endorsements;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EndorsementRepository extends CrudRepository<Endorsements,Long> {
    @Query(value = "SELECT * FROM endorsements WHERE users_has_talents_person_id = ?1 AND users_has_talents_talent_id = ?2", nativeQuery = true)
    List<Endorsements> findEndorsementsForUserTalent(int person_id, int talent_id);
}
