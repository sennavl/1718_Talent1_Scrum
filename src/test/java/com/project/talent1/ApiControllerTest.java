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
    @Autowired
    private EndorsementRepository endorsementRepository;

    private long personId;
    private long personId2;

    private long talentId;
    private long talentId2;
    private long talentId3;

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

        usersHasTalentsRepository.save(new Users_has_talents(personId2, talentId, "Dit is een van mijn talenten", 0));

        talentRepository.save(new Talents("andereNaam", 0L));
        this.talentId2 = talentRepository.findByName("andereNaam").getId();

        talentRepository.save(new Talents("doorzetter", 0L));
        this.talentId3 = talentRepository.findByName("doorzetter").getId();

        voteRepository.save(new Votes("Dit is de reden", personId2, personId, talentId2));
        this.voteId = voteRepository.findByText("Dit is de reden").getId();

        endorsementRepository.save(new Endorsements("Ik ga ermee akkoord dat dat zo is", personId, personId2, talentId));
    }

    @After
    public void after(){
        endorsementRepository.deleteAll();
        voteRepository.deleteAll();
        usersHasTalentsRepository.deleteAll();
        userRepository.deleteAll();
        personRepository.deleteAll();
        talentRepository.deleteAll();
    }

    /*============================================================================
        Users
    ============================================================================*/

    @Test
    public void getAllUsers() throws Exception{
        mockMvc.perform(get("/api/users"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getOneUser() throws Exception{
        mockMvc.perform(get("/api/users/" + personId))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person_id", is(toIntExact(personId))))
                .andExpect(jsonPath("$.birthday", is("1997-06-01")))
                .andExpect(jsonPath("$.person.id", is(toIntExact(personId))))
                .andExpect(jsonPath("$.person.firstname", is("Senna")))
                .andExpect(jsonPath("$.person.lastname", is("Van Londersele")))
                .andExpect(jsonPath("$.person.email", is("senna@mail.be")));
    }

    @Test
    public void getOnePerson() throws Exception{
        mockMvc.perform(get("/api/persons/" + personId))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(toIntExact(personId))))
                .andExpect(jsonPath("$.firstname", is("Senna")))
                .andExpect(jsonPath("$.lastname", is("Van Londersele")))
                .andExpect(jsonPath("$.email", is("senna@mail.be")));
    }

    @Test
    public void getNonExistingUser() throws Exception{
        mockMvc.perform(get("/api/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getNonExistingPerson() throws Exception{
        mockMvc.perform(get("/api/persons/0"))
                .andExpect(status().isNotFound());
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
    }

    @Test
    public void registerUserMissingCredential() throws Exception{
        Users u = new Users();
        u.setPassword(BCrypt.hashpw("Azerty123", BCrypt.gensalt()));

        Persons p = new Persons();
        p.setEmail("senna2@mail.be");
        p.setFirstname("Senna");
        p.setLastname("Van Londersele");

        String jsonRegistration = TestHelper.registrationCredentialsToJson(u, p);

        mockMvc.perform(post("/api/users/register/")
                .content(jsonRegistration)
                .contentType(contentType))
                .andExpect(status().isConflict());
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
                .andExpect(status().isOk())
                .andExpect(cookie().exists("user"));
    }

    @Test
    public void addPerson() throws Exception{
        String email = "testperson@gmail.com";
        String firstname = "TestPersonVN";
        String lastname = "TestPersonAN";

        String jsonPerson = TestHelper.personToJson(email, firstname, lastname);

        mockMvc.perform(post("/api/persons/add/")
                .content(jsonPerson)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void addPersonMissingCredential() throws Exception{
        String email = "testperson@gmail.com";
        String firstname = "TestPersonVN";

        String jsonPerson = TestHelper.personToJson(email, firstname);

        mockMvc.perform(post("/api/persons/add/")
                .content(jsonPerson)
                .contentType(contentType))
                .andExpect(status().isConflict());
    }

    @Test
    public void logUserInWithWrongPassword() throws Exception{
        String email = "senna@mail.be";
        String password = "Qwerty123";

        String jsonLogin = TestHelper.loginCredentialsToJson(email, password);

        mockMvc.perform(post("/api/users/login/")
                .content(jsonLogin)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void logNonExistentUserIn() throws Exception{
        String email = "nonExistent@mail.be";
        String password = "Qwerty123";

        String jsonLogin = TestHelper.loginCredentialsToJson(email, password);

        mockMvc.perform(post("/api/users/login/")
                .content(jsonLogin)
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void logoutUser() throws Exception{
        this.logUserIn();

        mockMvc.perform(post("/api/users/logout"))
                .andExpect(cookie().maxAge("user",0));
    }

    @Test
    public void updateUser() throws Exception {
        Users user = new Users();

        user.setBirthday(Date.valueOf(LocalDate.parse("1997-06-01")));
        user.setPassword("Azerty123");

        Persons person = new Persons();
        person.setId(personId);
        person.setFirstname("Newname");
        person.setLastname("Van Londersele");

        String jsonUpdate = TestHelper.userUpdateToJson(user, person);

        mockMvc.perform(post("/api/users/update")
                .content(jsonUpdate)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person.firstname", is("Newname")));
    }

    /*============================================================================
        Talents
    ============================================================================*/

    @Test
    public void getAllTalents() throws Exception{
        mockMvc.perform(get("/api/talents"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getTop20Talents() throws Exception{
        for(int i = 0; i < 25; i++){
            talentRepository.save(new Talents("tal" + i + "ent", 0L));
        }

        mockMvc.perform(get("/api/talents/top20"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)));
    }

    @Test
    public void getOneTalent() throws Exception{
        mockMvc.perform(get("/api/talents/" + this.talentId))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(toIntExact(talentId))))
                .andExpect(jsonPath("$.name", is("getalenteerd")))
                .andExpect(jsonPath("$.matches", is(0)));
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
                .andExpect(jsonPath("$.name", is("Talent")))
                .andExpect(jsonPath("$.matches", is(0)));
    }

    @Test
    public void getAllTalentsOfUser() throws Exception{
        mockMvc.perform(get("/api/users/" + this.personId + "/talents"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    public Users_has_talents createUsersHasTalents(long talent_id){
        Users_has_talents userTalents = new Users_has_talents();
        userTalents.setTalentId(talent_id);
        userTalents.setDescription("Dit klopt");
        userTalents.setHide(0);
        return userTalents;
    }

    @Test
    public void addTalentToUser() throws Exception{
        String jsonUserTalent = TestHelper.userTalentToJson(createUsersHasTalents(talentId3));

        mockMvc.perform(post("/api/users/" + personId + "/talents/add")
                .content(jsonUserTalent)
                .contentType(contentType))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void addNonExistentTalentToUser() throws Exception{
        String jsonUserTalent = TestHelper.userTalentToJson(createUsersHasTalents(0));

        mockMvc.perform(post("/api/users/" + personId + "/talents/add")
                .content(jsonUserTalent)
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTalentFromUserNonExistentUser() throws Exception{
        mockMvc.perform(delete("/api/users/" + 0 + "/talents/" + talentId + "/delete"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTalentFromUserNonExistentTalent() throws Exception{
        mockMvc.perform(delete("/api/users/" + personId + "/talents/" + 0 + "/delete"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTalentFromUser() throws Exception{
        mockMvc.perform(delete("/api/users/" + personId + "/talents/" + talentId + "/delete"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTalentsOfUserWithEndorsements() throws Exception {
        mockMvc.perform(get("/api/users/" + personId2 + "/talentEndorsements"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].endorsementCounter", is(1)))
                .andExpect(jsonPath("$[0].talent.name", is("getalenteerd")))
                .andExpect(jsonPath("$[0].talent.matches", is(0)))
                .andExpect(jsonPath("$[0].talent.id", is(toIntExact(talentId))));
    }

    /*============================================================================
        Votes
    ============================================================================*/

    @Test
    public void getSuggestionsForUser() throws Exception{
        mockMvc.perform(get("/api/users/" + this.personId + "/suggestions"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

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

    /*============================================================================
        Endorsements
    ============================================================================*/

    @Test
    public void addEndorsement() throws Exception{
        Endorsements endorsement = new Endorsements();
        endorsement.setDescription("Dit talent past inderdaad bij deze persoon");
        endorsement.setPersons_id(personId2);
        endorsement.setUsers_has_talents_person_id(personId);
        endorsement.setUsers_has_talents_talent_id(talentId);

        String jsonEndorsement = TestHelper.endorsementToJson(endorsement);

        mockMvc.perform(post("/api/endorsement/add")
                .content(jsonEndorsement)
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void addIncompleteEndorsement() throws Exception{
        Endorsements endorsement = new Endorsements();
        endorsement.setDescription("Dit talent past inderdaad bij deze persoon");
        endorsement.setPersons_id(personId2);
        endorsement.setUsers_has_talents_person_id(personId);

        String jsonEndorsement = TestHelper.endorsementToJson(endorsement);

        mockMvc.perform(post("/api/endorsement/add")
                .content(jsonEndorsement)
                .contentType(contentType))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    public void getAllEndorsementsOfUserTalent() throws Exception{
        mockMvc.perform(get( "/api/users/" + this.personId2 + "/talents/" + this.talentId + "/endorsements/"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getAllEndorsementsOfUserTalentEmpty() throws Exception{
        mockMvc.perform(get( "/api/users/" + 0 + "/talents/" + 0 + "/endorsements/"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getNumberOfEndorsementsOfUserTalent() throws Exception{
        mockMvc.perform(get( "/api/users/" + this.personId2 + "/talents/" + this.talentId + "/endorsements/count"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }

    @Test
    public void getNumberOfEndorsementsOfUserTalentEmpty() throws Exception{
        mockMvc.perform(get( "/api/users/" + 0 + "/talents/" + 0 + "/endorsements/count"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(0)));
    }
}