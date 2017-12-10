package com.project.talent1;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.TalentRepository;
import com.project.talent1.Repositories.UserRepository;
import com.project.talent1.Repositories.UsersHasTalentsRepository;
import com.project.talent1.Utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.project.talent1.Models.*;

import static javax.servlet.http.HttpServletResponse.*;

@RequestMapping("/api")
@CrossOrigin(origins = "localhost")
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

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        return "Hello World!";
    }
    /*============================================================================
        Users
    ============================================================================*/
    @GetMapping(path="/users")
    public @ResponseBody Iterable<Users> getAllUsers(){
        return users.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public Users getUser(@PathVariable long id){
        return users.findByPerson_id(id);
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

    @RequestMapping(path = "/users/login",method = RequestMethod.POST)
    public Users login(@RequestBody String json, HttpServletResponse response) throws IOException {
        String password = JsonHelper.getStringOutJson("password",json);
        String email = JsonHelper.getStringOutJson("email",json);

        Users user = users.findByPerson_id(persons.findByEmail(email).getId());
        user.login(response,password);

        return user;
    }

    /*============================================================================
        Talents
    ============================================================================*/
    @GetMapping(path = "/talents")
    public Iterable<Talents> getAllTalents(){
        return talents.findAll();
    }
    @GetMapping(path = "/talents/{id}")
    public Talents getTalent(@PathVariable long id){
        return talents.findById(id);
    }

    @RequestMapping(path = "/talents/add")
    public Talents addTalent(@RequestBody Talents t, HttpServletResponse response) throws IOException {
        if(talents.findByNameContaining(t.getName())==null){
            t.setMatches(Long.parseLong("0"));
            talents.save(t);
            return t;
        }else {
            response.sendError(404,"Already exists");
            return null;
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
        if(userTalent.getTalentId()==0){
            Talents t = JsonHelper.getTalentOutJson(json);

            //TODO: Toevoegen van Talent
        }else{
            userTalent.setPersonId(id);
            usersHasTalentsRepository.save(userTalent);
            Talents t = talents.findById(userTalent.getTalentId());
            t.setMatches(t.getMatches()+1);
            talents.save(t);
        }

        return getAllTalentsOfUser(id);
    }
    /*============================================================================
        Voters
    ============================================================================*/

    /*============================================================================
        Votes
    ============================================================================*/
    @GetMapping(path = "/test")
    public Iterable<Users_has_talents> getUsersTalents(){
        return usersHasTalentsRepository.findAll();
    }

}
