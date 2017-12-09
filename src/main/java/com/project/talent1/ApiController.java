package com.project.talent1;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.TalentRepository;
import com.project.talent1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

            person.setId(Long.parseLong("0"));
            persons.save(person);
            person=persons.findByEmail(person.getEmail());
            user.setPerson_id(person.getId());
            users.save(user);
            person.setId(person.getId());

            return user;
        }catch (Exception e){
            response.sendError(SC_CONFLICT,e.getMessage());
            return null;
        }

    }

    /*
    @RequestMapping(path = "/users/login",method = RequestMethod.POST)
    public Users login(@RequestBody Users inputUser, HttpServletResponse response) throws IOException {
        Users fullUser = users.findByEmail(inputUser.getEmail());
        fullUser.login(response,inputUser.getPassword());
        return fullUser;
    }
    */
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
    /*============================================================================
        Voters
    ============================================================================*/

    /*============================================================================
        Votes
    ============================================================================*/


}
