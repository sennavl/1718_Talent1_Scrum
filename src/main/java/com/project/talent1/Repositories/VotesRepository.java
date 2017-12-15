package com.project.talent1.Repositories;

import com.project.talent1.Models.Votes;
import org.springframework.data.repository.CrudRepository;

public interface VotesRepository extends CrudRepository<Votes,Long>{
    Votes findById(long id);

    Votes findByText(String text);
}
