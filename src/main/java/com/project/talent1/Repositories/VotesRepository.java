package com.project.talent1.Repositories;

import com.project.talent1.Models.Votes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VotesRepository extends CrudRepository<Votes,Long>{
    Votes findById(long id);

    Votes findByText(String text);

    @Query(value = "SELECT * FROM votes WHERE users_has_talents_person_id = ?1", nativeQuery = true)
    Iterable<Votes> findSuggestionsForUser(long id);
}
