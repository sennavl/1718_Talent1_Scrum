package com.project.talent1;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.TalentRepository;
import com.project.talent1.Repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.IOException;
import com.project.talent1.Models.*;
import org.springframework.web.context.request.WebRequest;

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
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            Users user = mapper.convertValue(node.get("user"), Users.class);
            Persons person = mapper.convertValue(node.get("person"), Persons.class);
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

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        String email = mapper.convertValue(node.get("email"), String.class);
        String password = mapper.convertValue(node.get("password"), String.class);

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

    @RequestMapping(path = "/user/{user_id}/talent/{talent_id}/description/{description}",method = RequestMethod.POST)
    public void addTalentToUser(long user_id, long talent_id, String description){
        Users u = users.findByPerson_id(user_id);
        Talents t = talents.findById(talent_id);

        UsersTalents ut = new UsersTalents();

        ut.setTalent(t);
        ut.setUser(u);
        ut.setHide("0");
        ut.setDescription(description);

        u.getUserTalents().add(ut);

        users.save(u);
        talents.save(t);
    }
    /*============================================================================
        Voters
    ============================================================================*/

    /*============================================================================
        Votes
    ============================================================================*/


}
