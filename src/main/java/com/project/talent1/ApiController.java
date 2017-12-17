package com.project.talent1;


import com.project.talent1.Repositories.*;
import com.project.talent1.Utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.project.talent1.Models.*;

import static javax.servlet.http.HttpServletResponse.*;

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
    @GetMapping(path="/users")
    public @ResponseBody Iterable<Users> getAllUsers(){
        return users.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public Users getUser(@PathVariable long id){
        Users user = users.findByPerson_id(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @RequestMapping(path = "/users/register",method = RequestMethod.POST)
    public Users registerUser(@RequestBody String json, HttpServletResponse response) throws IOException {
        try {
            Users user = JsonHelper.getUserOutJson(json);
            Persons person = JsonHelper.getPersonOutJson(json);
            user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));

            persons.save(person);
            person=persons.findByEmail(person.getEmail());
            user.setPerson_id(person.getId());
            users.save(user);

            return users.findByPerson_id(persons.findByEmail(person.getEmail()).getId());
        }catch (Exception e){
            response.sendError(SC_CONFLICT,e.getMessage());
            return null;
        }
    }

    @RequestMapping(path = "/persons/add",method = RequestMethod.POST)
    public Persons registerPerson(@RequestBody Persons person, HttpServletResponse response) throws IOException {
        try {
            persons.save(person);
            person=persons.findByEmail(person.getEmail());
            return person;
        }catch (Exception e){
            response.sendError(SC_CONFLICT,e.getMessage());
            return null;
        }
    }

    @RequestMapping(path = "/users/login",method = RequestMethod.POST)
    public Long login(@RequestBody String json, HttpServletResponse response) throws IOException {
        String password = JsonHelper.getStringOutJson("password",json);
        String email = JsonHelper.getStringOutJson("email",json);

        Users user = users.findByPerson_id(persons.findByEmail(email).getId());
        user.login(response,password);

        Cookie userCookie = new Cookie("user", user.getPerson_id().toString());
        //setting cookie to expiry in 30 mins
        userCookie.setMaxAge(30*60);
        response.addCookie(userCookie);

        return user.getPerson_id();
    }

    /*============================================================================
        Talents
    ============================================================================*/
    @GetMapping(path = "/talents")
    public Iterable<Talents> getAllTalents(){
        return talents.findAll();
    }

    @GetMapping(path = "/talents/top20")
    public Iterable<Talents> getTop20Talents(){
        return talents.findTop20();
    }

    @GetMapping(path = "/talents/{id}")
    public Talents getTalent(@PathVariable long id){
        return talents.findById(id);
    }

    @RequestMapping(path = "/talents/add")
    public Talents addTalent(@RequestBody Talents t) throws IOException {
        Talents fetchedTalent = talentExistsAndFetch(t.getName());
        if(fetchedTalent==null){
            t.setMatches(Long.parseLong("0"));
            talents.save(t);
            return talents.findByName(t.getName());
        }else {
            return fetchedTalent;
        }
    }

    @GetMapping(path = "/users/{id}/talents")
    public Iterable<Talents> getAllTalentsOfUser(@PathVariable long id){
        Iterable<Users_has_talents> items= usersHasTalentsRepository.findAllByPersonId(id);
        List<Talents> ouput = new ArrayList<Talents>();
        for (Users_has_talents u: items) {
            if(u.getHide()==(0)){
                ouput.add(talents.findById(u.getTalentId()));
            }
        }
        return ouput;
    }

    @RequestMapping(path = "/users/{id}/talents/add")
    public Iterable<Talents> addUserTalent(@RequestBody String json,@PathVariable long id) throws IOException {
        Users_has_talents userTalent = JsonHelper.getUserTalentOutJson(json);
        Talents t;
        if(userTalent.getTalentId()==0){
            t = JsonHelper.getTalentOutJson(json);

            t=addTalent(t);
        }
        if(talents.findById(userTalent.getTalentId())!=null){
            userTalent.setPersonId(id);
            usersHasTalentsRepository.save(userTalent);
            t = talents.findById(userTalent.getTalentId());
            t.setMatches(t.getMatches()+1);
            talents.save(t);
        }

        return getAllTalentsOfUser(id);
    }

    public Talents talentExistsAndFetch(String talent){
        Talents searchTalent=talents.findByNameContaining(talent);
        if(searchTalent==null){
            return talents.findByNameContaining(talent);
        }
        for (Talents t:talents.findAll()) {
            if(talent.contains(t.getName())){
                return t;
            }
        }
        return null;
    }
    /*============================================================================
        Voters
    ============================================================================*/

    /*============================================================================
        Votes
    ============================================================================*/
    @RequestMapping(path = "/users/suggest")
    public void addSuggestion(@RequestBody Votes vote){
        vote.setId(0L);
        votes.save(vote);
    }
    @RequestMapping(path = "/users/processSugestion")
    public void reactToSuggestion(@RequestBody String json) throws IOException {
        long voteId= Long.parseLong(JsonHelper.getStringOutJson("voteId",json));
        boolean accepted= Boolean.parseBoolean(JsonHelper.getStringOutJson("accepted",json));
        Votes vote = votes.findById(voteId);
        if(vote!=null){
            if(accepted){
                boolean hide= Boolean.parseBoolean(JsonHelper.getStringOutJson("hide",json));
                vote.AcceptVote(votes,usersHasTalentsRepository,hide);
            }else{
                vote.RefuseVote(votes);
            }
        }
    }

    /*============================================================================
        Endorsements
    ============================================================================*/
    @RequestMapping(path = "/endorsement/add")
    public void addEndorsement(@RequestBody Endorsements endorsement, HttpServletResponse response) throws IOException {
        try{
            endorsement.setId(0L);
            endorsements.save(endorsement);
        }catch(Exception e){
            response.sendError(SC_EXPECTATION_FAILED,e.getMessage());
        }
    }

    // voorbeeld voor in postman: http://localhost:8080/api/users/4/talents/2/endorsements/
    // vraag de endorsements op die voor een bepaald talent van een bepaalde user gebeurd zijn
    @GetMapping(path = "/users/{person_id}/talents/{talent_id}/endorsements")
    public Iterable<Endorsements> getAllEndorsementsOfUserTalent(@PathVariable long person_id, @PathVariable long talent_id) {
        List<Endorsements> endorsementsUserTalent = endorsements.findEndorsementsForUserTalent((int)person_id, (int)talent_id);
        return endorsementsUserTalent;
    }

    // voorbeeld voor in postman: http://localhost:8080/api/users/4/talents/2/endorsements/count/
    @GetMapping(path = "/users/{person_id}/talents/{talent_id}/endorsements/count")
    public int getNumberOfEndorsementsOfUserTalent(@PathVariable long person_id, @PathVariable long talent_id){
        int endorsementsCount = endorsements.findAmountOfEndorsementsForUserTalent((int)person_id, (int)talent_id);
        return endorsementsCount;
    }

    @GetMapping(path="/endorsements")
    public @ResponseBody Iterable<Endorsements> getAllEndorsements(){
        return endorsements.findAll();
    }


    /*============================================================================
        Status
    ============================================================================*/
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class UserNotFoundException extends RuntimeException {

        public UserNotFoundException(long userId) {
            super("could not find user '" + userId + "'.");
        }
    }
}
