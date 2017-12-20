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
import javax.servlet.http.HttpSession;
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

    HttpSession session;


    /*============================================================================
        Users
    ============================================================================*/
    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<Users> getAllUsers() {
        if (session != null) {
            return users.findAll();
        }
        return null;
    }

    @GetMapping(path = "/users/search/")
    Iterable<Users> searchUserDefault(){
        return searchUser("");
    }
    @GetMapping(path = "/users/search/{needle}")
    public @ResponseBody
    Iterable<Users> searchUser(@PathVariable String needle) {
        if (session != null) {
            List<Persons> people = persons.getPeople(needle);
            List<Users> ouput = StreamSupport.stream(people.spliterator(), false)
                    .filter(person -> users.findByPerson_id(person.getId()) != null)
                    .map(person -> users.findByPerson_id(person.getId()))
                    .collect(toList());
            return ouput;
        }
        return null;
    }

    @GetMapping(path = "/users/{id}")
    public Users getUser(@PathVariable long id) {
        if (session != null) {
            Users user = users.findByPerson_id(id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            return user;
        }
        return null;
    }

    @RequestMapping(path = "/users/register", method = RequestMethod.POST)
    public Users registerUser(@RequestBody String json, HttpServletResponse response) throws IOException {
        if (session != null) {
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
        return null;
    }

    @RequestMapping(path = "/persons/add", method = RequestMethod.POST)
    public Persons registerPerson(@RequestBody Persons person, HttpServletResponse response) throws IOException {
        if (session != null) {
            try {
                return person.register(persons);
            } catch (Exception e) {
                response.sendError(SC_CONFLICT, e.getMessage());
                return null;
            }
        }
        return null;
    }

    @GetMapping(path = "/persons/{id}")
    public Persons getPerson(@PathVariable long id) {
        if (session != null) {
            Persons person = persons.findById(id);
            if (person == null) {
                throw new PersonNotFoundException(id);
            }
            return person;
        }
        return null;
    }

    @RequestMapping(path = "/users/login", method = RequestMethod.POST)
    public Long login(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String password = JsonHelper.getStringOutJson("password", json);
        String email = JsonHelper.getStringOutJson("email", json);
        try {
            Users user = users.findByPerson_id(persons.findByEmail(email).getId());
            user.login(response, password);

            session = request.getSession();
            session.setAttribute("user", user.person.getFirstname());
            session.setMaxInactiveInterval(30*60);

            return user.getPerson_id();
        } catch (NullPointerException e) {
            throw new UserNotFoundException(email);
        }
    }

    @RequestMapping(path = "/users/logout", method = RequestMethod.POST)
    public void logOut(HttpServletResponse response) throws InterruptedException {
        if (session != null) {
            Cookie userCookie = new Cookie("user", null);
            userCookie.setMaxAge(0);
            response.addCookie(userCookie);
            session.removeAttribute("user");
            session.invalidate();
            session = null;
        }
    }

    @RequestMapping(path = "/users/update")
    public Users update(@RequestBody Users user, HttpServletResponse response) {
        if (session != null) {
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
        return null;
    }

    /*============================================================================
        Talents
    ============================================================================*/
    @GetMapping(path = "/talents")
    public Iterable<Talents> getAllTalents() {
        if (session != null) {
            return talents.findAll();
        }
        return null;
    }

    @GetMapping(path = "/talents/top20")
    public Iterable<Talents> getTop20Talents() {
        if (session != null) {
            return talents.findTop20();
        }
        return null;
    }

    @GetMapping(path = "/talents/{id}")
    public Talents getTalent(@PathVariable long id) {
        if (session != null) {
            return talents.findById(id);
        }
        return null;
    }

    @RequestMapping(path = "/talents/add")
    public Talents addTalent(@RequestBody Talents talent) {
        if (session != null) {
            Talents fetchedTalent = checkIfTalentExistsAndFetch(talent.getName());
            if (fetchedTalent == null) {
                talent.setMatches(Long.parseLong("0"));
                talents.save(talent);
                return talents.findByName(talent.getName());
            } else {
                return fetchedTalent;
            }
        }
        return null;
    }

    @RequestMapping(path = "/users/{user_id}/talents/{talent_id}/delete", method = RequestMethod.DELETE)
    public void deleteTalent(@PathVariable int user_id, @PathVariable int talent_id) {
        if (session != null) {
            Users user = users.findByPerson_id(user_id);
            if (user == null) {
                throw new UserNotFoundException(user_id);
            }
            Talents talent = talents.findById(talent_id);
            if (talent == null) {
                throw new TalentNotFoundException(talent_id);
            }
            endorsements.delete(endorsements.findEndorsementsForUserTalent(user_id, talent_id));
            Users_has_talents userTalent = usersHasTalentsRepository.findByPersonIdTalentId(user_id, talent_id);
            usersHasTalentsRepository.delete(userTalent);
        }
    }

    @GetMapping(path = "/users/{id}/talents")
    public Iterable<Talents> getAllTalentsOfUser(@PathVariable long id) {
        if (session != null) {
            Iterable<Users_has_talents> items = usersHasTalentsRepository.findAllByPersonId(id);
            List<Talents> ouput = StreamSupport.stream(items.spliterator(), false)
                    .filter(userTalent -> userTalent.getHide() == 0)
                    .map(usertalent -> (talents.findById(usertalent.getTalentId())))
                    .collect(toList());

            return ouput;
        }
        return null;
    }

    @GetMapping(path = "/users/{id}/talentEndorsements")
    public Iterable<TalentAndEndorsement> getAllTalentsOfUserWithEndorsements(@PathVariable long id) {
        if (session != null) {
            Iterable<Users_has_talents> items = usersHasTalentsRepository.findAllByPersonId(id);
            List<TalentAndEndorsement> ouput = StreamSupport.stream(items.spliterator(), false)
                    .filter(userTalent -> userTalent.getHide() == 0)
                    .map(usertalent -> (new TalentAndEndorsement(talents.findById(usertalent.getTalentId()), getNumberOfEndorsementsOfUserTalent(usertalent.getPersonId(), usertalent.getTalentId()))))
                    .collect(toList());

            return ouput;
        }
        return null;
    }

    @RequestMapping(path = "/users/{id}/talents/add")
    public Iterable<Talents> addUserTalent(@RequestBody Users_has_talents userTalent, @PathVariable long id) throws IOException {
        if (session != null) {
            long talentId = userTalent.getTalentId();
            if (talents.findById(talentId) == null) {
                throw new TalentNotFoundException(talentId);
            } else {
                userTalent.register(talents.findById(talentId), id, talents, usersHasTalentsRepository);
            }
            return getAllTalentsOfUser(id);
        }
        return null;
    }

    public Talents checkIfTalentExistsAndFetch(String talent) {
        if (session != null) {
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
        return null;
    }

    /*============================================================================
        Votes
    ============================================================================*/

    @GetMapping(path = "/users/{id}/suggestions")
    public Iterable<Votes> getAllSuggestionsForUser(@PathVariable long id) {
        if (session != null) {
            return votes.findSuggestionsForUser(id);
        }
        return null;
    }

    @RequestMapping(path = "/users/suggest")
    public void addSuggestion(@RequestBody Votes vote) {
        if (session != null) {
            vote.setId(0L);
            votes.save(vote);
        }
    }

    @RequestMapping(path = "/users/processSugestion")
    public void reactToSuggestion(@RequestBody String json) throws IOException {
        if (session != null) {
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
    }

    /*============================================================================
        Endorsements
    ============================================================================*/
    @RequestMapping(path = "/endorsement/add")
    public void addEndorsement(@RequestBody Endorsements endorsement, HttpServletResponse response) throws IOException {
        if (session != null) {
            try {
                endorsement.setId(0L);
                endorsements.save(endorsement);
            } catch (Exception e) {
                response.sendError(SC_EXPECTATION_FAILED, e.getMessage());
            }
        }
    }

    @GetMapping(path = "/users/{person_id}/talents/{talent_id}/endorsements")
    public Iterable<Endorsements> getAllEndorsementsOfUserTalent(@PathVariable long person_id, @PathVariable long talent_id) {
        if (session != null) {
            return endorsements.findEndorsementsForUserTalent((int) person_id, (int) talent_id);
        }
        return null;
    }

    @GetMapping(path = "/users/{person_id}/talents/{talent_id}/endorsements/count")
    public int getNumberOfEndorsementsOfUserTalent(@PathVariable long person_id, @PathVariable long talent_id) {
        if (session != null) {
            return endorsements.findAmountOfEndorsementsForUserTalent((int) person_id, (int) talent_id);
        }
        return 0;
    }

    @GetMapping(path = "/getSession")
    public Object getSession() {

        if (session != null) {
            return session.getAttribute("user");
        }

        return session;
    }
}