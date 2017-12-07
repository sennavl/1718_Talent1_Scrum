package com.project.talent1;

import com.project.talent1.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@RequestMapping("/api")
@CrossOrigin(origins = "localhost")
@RestController
public class ApiController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/users")
    public @ResponseBody Iterable<Users> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public @ResponseBody
    Users getUser(@PathVariable long id){
        return userRepository.findById(id);
    }

    @RequestMapping(path = "/users/register",method = RequestMethod.POST)
    public @ResponseBody
    Users registerUser(@RequestBody Users s, HttpServletResponse response) throws IOException {
        try {
            s.setPassword(BCrypt.hashpw(s.getPassword(),BCrypt.gensalt()));
            return userRepository.save(s);
        }catch (Exception e){
            response.sendError(SC_CONFLICT,e.getMessage());
            return null;
        }

    }

    @RequestMapping(path = "/users/login",method = RequestMethod.POST)
    public Users login(@RequestBody Users inputUser, HttpServletResponse response) throws IOException {
        Users fullUser = userRepository.findByEmail(inputUser.getEmail());
        fullUser.login(response,inputUser.getPassword());
        return fullUser;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        return "Hello World!";
    }
}
