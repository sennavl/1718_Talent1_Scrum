package com.project.talent1;

import com.project.talent1.Models.*;
import com.project.talent1.Repositories.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.sql.Date;
import static org.hamcrest.Matchers.*;
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
    @Autowired
    private TalentRepository talentRepository;
    @Autowired
    private UsersHasTalentsRepository usersHasTalentsRepository;
    @Autowired
    private VotesRepository voteRepository;

    private long personId;
    private long personId2;

    private long talentId;

    private long talentId2;

    private long voteId;

    @Before
    public void setup(){

        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        personRepository.save(new Persons("Senna", "Van Londersele", "senna@mail.be"));
        this.personId = personRepository.findByEmail("senna@mail.be").getId();

        userRepository.save(new Users(personId, Date.valueOf(LocalDate.parse("1997-06-01")), BCrypt.hashpw("Azerty123", BCrypt.gensalt())));

        personRepository.save(new Persons("Peter", "Peeters", "peterp@mail.be"));
        this.personId2 = personRepository.findByEmail("peterp@mail.be").getId();

        userRepository.save(new Users(personId2, Date.valueOf(LocalDate.parse("1990-05-04")), BCrypt.hashpw("Azerty123", BCrypt.gensalt())));

        talentRepository.save(new Talents("getalenteerd", 0L));
        this.talentId = talentRepository.findByName("getalenteerd").getId();

        usersHasTalentsRepository.save(new Users_has_talents(personId, talentId, "Dit is mijn talent", 0));

        talentRepository.save(new Talents("andereNaam", 0L));
        this.talentId2 = talentRepository.findByName("andereNaam").getId();

        voteRepository.save(new Votes("Dit is de reden", personId2, personId, talentId2));
        this.voteId = voteRepository.findByText("Dit is de reden").getId();
    }

    @After
    public void after(){
        try {
            voteRepository.delete(voteId);
        }catch(Exception e){
            System.out.println("Error deleting vote: \n" + e.getMessage());
        }
        Iterable<Users_has_talents> utDel = usersHasTalentsRepository.findAllByPersonId(personId);
        usersHasTalentsRepository.delete(utDel);
        userRepository.delete(personId);
        personRepository.delete(personId);
        userRepository.delete(personId2);
        personRepository.delete(personId2);
        talentRepository.delete(talentId2);
        talentRepository.delete(talentId);
    }

    /*============================================================================
        Users
    ============================================================================*/

    @Test
    public void getAllUsers() throws Exception{
        mockMvc.perform(get("/api/users"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneUser() throws Exception{
        mockMvc.perform(get("/api/users/" + personId))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person_id", is(toIntExact(personId))));
    }

    @Test
    public void registerUser() throws Exception{
        Users u = new Users();
        u.setPassword(BCrypt.hashpw("Azerty123", BCrypt.gensalt()));
        u.setBirthday(Date.valueOf(LocalDate.parse("1997-06-01")));

        Persons p = new Persons();
        p.setEmail("senna2@mail.be");
        p.setFirstname("Senna");
        p.setLastname("Van Londersele");

        String jsonRegistration = TestHelper.registrationCredentialsToJson(u, p);

        mockMvc.perform(post("/api/users/register/")
                .content(jsonRegistration)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthday", is("1997-06-01")));

        Persons pDel = personRepository.findByEmail("senna2@mail.be");
        Users uDel = userRepository.findByPerson_id(pDel.getId());

        userRepository.delete(uDel);
        personRepository.delete(pDel);
    }

    @Test
    public void logUserIn() throws Exception{
        String email = "senna@mail.be";
        String password = "Azerty123";

        String jsonLogin = TestHelper.loginCredentialsToJson(email, password);

        mockMvc.perform(post("/api/users/login/")
                .content(jsonLogin)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    /*============================================================================
        Talents
    ============================================================================*/

    @Test
    public void getAllTalents() throws Exception{
        mockMvc.perform(get("/api/talents"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getTop20Talents() throws Exception{
        mockMvc.perform(get("/api/talents/top20"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneTalent() throws Exception{
        mockMvc.perform(get("/api/talents/" + this.talentId))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void addTalent() throws Exception{
        String name = "Talent";

        String jsonTalent = TestHelper.talentToJson(name);

        mockMvc.perform(post("/api/talents/add")
                .content(jsonTalent)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Talent")));

        Talents tDel = talentRepository.findByName("Talent");

        talentRepository.delete(tDel);
    }

    @Test
    public void getAllTalentsOfUser() throws Exception{
        mockMvc.perform(get("/api/users/" + this.personId + "/talents"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void addTalentToUser() throws Exception{
        Users_has_talents userTalents = new Users_has_talents();
        userTalents.setTalentId(2);
        userTalents.setDescription("Dit klopt");
        userTalents.setHide(0);

        String jsonUserTalent = TestHelper.userTalentToJson(userTalents);

        mockMvc.perform(post("/api/users/" + personId + "/talents/add")
                .content(jsonUserTalent)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    /*============================================================================
        Votes
    ============================================================================*/

    @Test
    public void addSuggestion() throws Exception{
        Votes vote = new Votes();
        vote.setText("Dat is inderdaad waar");
        vote.setPerson_id(personId);
        vote.setUsers_has_talents_person_id(personId2);
        vote.setUsers_has_talents_talent_id(talentId);

        String jsonVote = TestHelper.voteToJson(vote);

        mockMvc.perform(post("/api/users/suggest")
                .content(jsonVote)
                .contentType(contentType))
                .andExpect(status().isOk());

        long talentId = voteRepository.findByText("Dat is inderdaad waar").getId();
        voteRepository.delete(talentId);
    }

    @Test
    public void acceptSuggestion() throws Exception{

        String jsonReaction = TestHelper.reactionToSuggestionToJson(voteId, true, false);

        mockMvc.perform(post("/api/users/processSugestion")
                .content(jsonReaction)
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void refuseSuggestion() throws Exception{
        String jsonReaction = TestHelper.reactionToSuggestionToJson(voteId, false);

        mockMvc.perform(post("/api/users/processSugestion")
                .content(jsonReaction)
                .contentType(contentType))
                .andExpect(status().isOk());
    }
}