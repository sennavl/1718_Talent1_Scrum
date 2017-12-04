package com.project.talent1;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api")
@RestController
public class ApiController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/users")
    public @ResponseBody Iterable<Users> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public @ResponseBody Users getUser(@PathVariable long id){
        return userRepository.findById(id);
    }

    @RequestMapping(path = "/users/register",method = RequestMethod.POST)
    public @ResponseBody Users registerUser(@RequestBody Users s,HttpServletResponse response){
        try {
            s.setPassword(BCrypt.hashpw(s.getPassword(),BCrypt.gensalt()));
            return userRepository.save(s);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return null;
        }

    }

    @RequestMapping(path = "/users/login",method = RequestMethod.POST)
    public Users login(@RequestBody Users u,HttpServletResponse response) {
        Users s = userRepository.findByEmail(u.getEmail());
        try{
            if(BCrypt.checkpw(u.getPassword(),s.getPassword())){
                return s;
            }
            else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
        }catch (NullPointerException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        return "Hello World!";
    }
}
