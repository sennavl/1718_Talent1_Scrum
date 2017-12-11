package com.project.talent1;

import com.project.talent1.Models.Persons;
import com.project.talent1.Models.Users;
import com.project.talent1.Repositories.PersonRepository;
import com.project.talent1.Repositories.UserRepository;
import com.project.talent1.Utils.JsonHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static java.lang.Math.toIntExact;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Talent1Application.class)
@WebAppConfiguration
public class ApiControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllUsers() throws Exception{
        mockMvc.perform(get("/api/users"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneUser() throws Exception{
        mockMvc.perform(get("/api/users/4"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void registerUser() throws Exception{
        Users u = new Users();
        u.setPassword("Azerty123");
        u.setBirthday(Date.valueOf(LocalDate.parse("1997-06-01")));

        Persons p = new Persons();
        p.setEmail("senna@mail.be");
        p.setFirstname("Senna");
        p.setLastname("Van Londersele");

        String jsonRegistration = JsonHelper.registrationCredentialsToJson(u, p);

        mockMvc.perform(post("/api/users/register/")
                .content(jsonRegistration)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());

        Persons pDel = personRepository.findByEmail("senna@mail.be");
        Users uDel = userRepository.findByPerson_id(pDel.getId());

        userRepository.delete(uDel);
        personRepository.delete(pDel);
    }

    @Test
    public void logUserIn() throws Exception{
        String email = "janrobert422@gmail.com";
        String password = "Azerty123";

        String jsonLogin = JsonHelper.loginCredentialsToJson(email, password);

        mockMvc.perform(post("/api/users/login/")
                .content(jsonLogin)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }
}