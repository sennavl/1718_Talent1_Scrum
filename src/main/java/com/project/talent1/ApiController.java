package com.project.talent1;


import com.project.talent1.CustomExceptions.PersonNotFoundException;
import com.project.talent1.CustomExceptions.TalentNotFoundException;
import com.project.talent1.CustomExceptions.UserNotFoundException;
import com.project.talent1.Models.*;
import com.project.talent1.Repositories.*;
import com.project.talent1.Utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_EXPECTATION_FAILED;

@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RestController
public class ApiController {
    @Autowired
    UserRepository users;
    @Autowired
    TalentRepository talents;
    @Autowired
    PersonRepository persons;
    @Autowired
    UsersHasTalentsRepository usersHasTalentsRepository;
    @Autowired
    VotesRepository votes;
    @Autowired
    EndorsementRepository endorsements;


    /*============================================================================
        Users
    ============================================================================*/
    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<Users> getAllUsers() {
        return users.findAll();
    }

    @GetMapping(path = "/users/search/")
    Iterable<Users> searchUserDefault() {
        return searchUser("");
    }

    @GetMapping(path = "/users/search/{needle}")
    public @ResponseBody
    Iterable<Users> searchUser(@PathVariable String needle) {
        List<Persons> people = persons.getPeople(needle);
        List<Users> ouput = StreamSupport.stream(people.spliterator(), false)
                .filter(person -> users.findByPerson_id(person.getId()) != null)
                .map(person -> users.findByPerson_id(person.getId()))
                .collect(toList());
        return ouput;
    }

    @GetMapping(path = "/users/{id}")
    public Users getUser(@PathVariable long id) {
        Users user = users.findByPerson_id(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @RequestMapping(path = "/users/register", method = RequestMethod.POST)
    public Users registerUser(@RequestBody String json, HttpServletResponse response) throws IOException {
        try {
            Users user = JsonHelper.getUserOutJson(json);
            Persons person = JsonHelper.getPersonOutJson(json);
            user.register(response, person, users, persons);
            return getUser(user.getPerson_id());
        } catch (Exception e) {
            response.sendError(SC_CONFLICT, e.getMessage());
            return null;
        }
    }

    @RequestMapping(path = "/persons/add", method = RequestMethod.POST)
    public Persons registerPerson(@RequestBody Persons person, HttpServletResponse response) throws IOException {
        try {
            return person.register(persons);
        } catch (Exception e) {
            response.sendError(SC_CONFLICT, e.getMessage());
            return null;
        }
    }

    @GetMapping(path = "/persons/{id}")
    public Persons getPerson(@PathVariable long id) {
        Persons person = persons.findById(id);
        if (person == null) {
            throw new PersonNotFoundException(id);
        }
        return person;
    }

    @RequestMapping(path = "/users/login", method = RequestMethod.POST)
    public Long login(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String password = JsonHelper.getStringOutJson("password", json);
        String email = JsonHelper.getStringOutJson("email", json);
        try {
            Users user = users.findByPerson_id(persons.findByEmail(email).getId());
            user.login(response, password);
            return user.getPerson_id();
        } catch (NullPointerException e) {
            throw new UserNotFoundException(email);
        }
    }

    @RequestMapping(path = "/users/logout", method = RequestMethod.POST)
    public void logOut(HttpServletResponse response) throws InterruptedException {
        Cookie userCookie = new Cookie("user", null);
        userCookie.setMaxAge(0);
        response.addCookie(userCookie);
    }

    @RequestMapping(path = "/users/update")
    public Users update(@RequestBody Users user, HttpServletResponse response) {
        if (users.findByPerson_id(user.person.getId()) == null) {
            throw new UserNotFoundException(user.person.getId());
        } else {
            try {
                user.updateUser(users, persons);
                return user;
            } catch (NullPointerException e) {
                throw new NullPointerException("User details not filled in");
            }

        }
    }

    /*============================================================================
        Talents
    ============================================================================*/
    @GetMapping(path = "/talents")
    public Iterable<Talents> getAllTalents() {
        return talents.findAll();
    }

    @GetMapping(path = "/talents/top20")
    public Iterable<Talents> getTop20Talents() {
        return talents.findTop20();
    }

    @GetMapping(path = "/talents/{id}")
    public Talents getTalent(@PathVariable long id) {
        return talents.findById(id);
    }

    @RequestMapping(path = "/talents/add")
    public Talents addTalent(@RequestBody Talents talent) {
        Talents fetchedTalent = checkIfTalentExistsAndFetch(talent.getName());
        if (fetchedTalent == null) {
            talent.setMatches(Long.parseLong("0"));
            talents.save(talent);
            return talents.findByName(talent.getName());
        } else {
            return fetchedTalent;
        }
    }

    @RequestMapping(path = "/users/{user_id}/talents/{talent_id}/delete", method = RequestMethod.DELETE)
    public void deleteUserTalent(@PathVariable int user_id, @PathVariable int talent_id) {
        if (users.findByPerson_id(user_id) == null) {
            throw new UserNotFoundException(user_id);
        }
        if (talents.findById(talent_id) == null) {
            throw new TalentNotFoundException(talent_id);
        }
        endorsements.delete(endorsements.findEndorsementsForUserTalent(user_id, talent_id));
        Users_has_talents userTalent = usersHasTalentsRepository.findByPersonIdTalentId(user_id, talent_id);
        usersHasTalentsRepository.delete(userTalent);
    }

    @GetMapping(path = "/users/{id}/talents")
    public Iterable<Talents> getAllTalentsOfUser(@PathVariable long id) {
        Iterable<Users_has_talents> items = usersHasTalentsRepository.findAllByPersonId(id);
        List<Talents> output = StreamSupport.stream(items.spliterator(), false)
                .filter(userTalent -> userTalent.getHide() == 0)
                .map(usertalent -> (talents.findById(usertalent.getTalentId())))
                .collect(toList());

        return output;
    }

    @GetMapping(path = "/users/{id}/talentEndorsements")
    public Iterable<TalentAndEndorsement> getAllTalentsOfUserWithEndorsements(@PathVariable long id) {
        Iterable<Users_has_talents> items = usersHasTalentsRepository.findAllByPersonId(id);
        List<TalentAndEndorsement> output = StreamSupport.stream(items.spliterator(), false)
                .filter(userTalent -> userTalent.getHide() == 0)
                .map(usertalent -> (new TalentAndEndorsement(talents.findById(usertalent.getTalentId()), getNumberOfEndorsementsOfUserTalent(usertalent.getPersonId(), usertalent.getTalentId()))))
                .collect(toList());

        return output;
    }

    @RequestMapping(path = "/users/{id}/talents/add")
    public Iterable<Talents> addUserTalent(@RequestBody Users_has_talents userTalent, @PathVariable long id) throws IOException {
        long talentId = userTalent.getTalentId();
        if (talents.findById(talentId) == null) {
            throw new TalentNotFoundException(talentId);
        } else {
            userTalent.register(talents.findById(talentId), id, talents, usersHasTalentsRepository);
        }
        return getAllTalentsOfUser(id);
    }

    public Talents checkIfTalentExistsAndFetch(String talent) {
        Talents searchTalent = talents.findByNameContaining(talent);
        if (searchTalent == null) {
            return talents.findByNameContaining(talent);
        }
        for (Talents t : talents.findAll()) {
            if (talent.contains(t.getName())) {
                return t;
            }
        }
        return null;
    }

    /*============================================================================
        Votes
    ============================================================================*/

    @GetMapping(path = "/users/{id}/suggestions")
    public Iterable<Votes> getAllSuggestionsForUser(@PathVariable long id) {
        return votes.findSuggestionsForUser(id);
    }

    @RequestMapping(path = "/users/suggest")
    public void addSuggestion(@RequestBody Votes vote) {
        vote.setId(0L);
        votes.save(vote);
    }

    @RequestMapping(path = "/users/processSugestion")
    public void reactToSuggestion(@RequestBody String json) throws IOException {
        long voteId = Long.parseLong(JsonHelper.getStringOutJson("voteId", json));
        boolean accepted = Boolean.parseBoolean(JsonHelper.getStringOutJson("accepted", json));
        Votes vote = votes.findById(voteId);
        if (vote != null) {
            if (accepted) {
                boolean hide = Boolean.parseBoolean(JsonHelper.getStringOutJson("hide", json));
                vote.AcceptVote(votes, usersHasTalentsRepository, hide);
            } else {
                vote.RefuseVote(votes);
            }
        }
    }

    /*============================================================================
        Endorsements
    ============================================================================*/
    @RequestMapping(path = "/endorsement/add")
    public void addEndorsement(@RequestBody Endorsements endorsement, HttpServletResponse response) throws IOException {
        try {
            endorsement.setId(0L);
            endorsements.save(endorsement);
        } catch (Exception e) {
            response.sendError(SC_EXPECTATION_FAILED, e.getMessage());
        }
    }

    @GetMapping(path = "/users/{person_id}/talents/{talent_id}/endorsements")
    public Iterable<Endorsements> getAllEndorsementsOfUserTalent(@PathVariable long person_id, @PathVariable long talent_id) {
        return endorsements.findEndorsementsForUserTalent((int) person_id, (int) talent_id);
    }

    @GetMapping(path = "/users/{person_id}/talents/{talent_id}/endorsements/count")
    public int getNumberOfEndorsementsOfUserTalent(@PathVariable long person_id, @PathVariable long talent_id) {
        return endorsements.findAmountOfEndorsementsForUserTalent((int) person_id, (int) talent_id);
    }
}