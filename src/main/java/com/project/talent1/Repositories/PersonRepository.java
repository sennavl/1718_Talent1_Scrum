package com.project.talent1.Repositories;

import com.project.talent1.Models.Persons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PersonRepository extends CrudRepository<Persons, Long> {
    Persons findByEmail(String email);

    Persons findById(long id);

    @Query(nativeQuery = true, value = "select * from persons where firstname like  %:needle% or lastname like %:needle%")
    List<Persons> getPeople(@Param("needle") String needle);
}
