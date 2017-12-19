package com.project.talent1;

import com.project.talent1.Models.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class TestHelper {

    /*============================================================================
        Users/persons
    ============================================================================*/

    public static String loginCredentialsToJson(String email, String password){
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("password", password);
        return json.toString();
    }

    public static String personToJson(String email, String firstname, String lastname){
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("firstname", firstname);
        json.put("lastname", lastname);
        return json.toString();
    }

    public static String personToJson(String email, String firstname){
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("firstname", firstname);
        return json.toString();
    }

    public static String registrationCredentialsToJson(Users u, Persons p){
        JSONObject json = new JSONObject();

        JSONArray arrayPerson = new JSONArray();
        JSONObject itemPerson = new JSONObject();
        itemPerson.put("firstname", p.getFirstname());
        itemPerson.put("lastname", p.getLastname());
        itemPerson.put("email", p.getEmail());
        arrayPerson.add(itemPerson);

        json.put("person", arrayPerson);

        JSONArray arrayUser = new JSONArray();
        JSONObject itemUser = new JSONObject();
        if(u.getBirthday()!= null) itemUser.put("birthday", u.getBirthday().toString());
        itemUser.put("password", u.getPassword());
        arrayUser.add(itemUser);

        json.put("user", arrayUser);

        String test = json.toString();

        return json.toString().replace("[", "").replace("]", "");
    }

    public static String userUpdateToJson(Users u, Persons p){
        JSONObject json = new JSONObject();

        JSONArray arrayPerson = new JSONArray();
        JSONObject itemPerson = new JSONObject();
        itemPerson.put("id", p.getId());
        itemPerson.put("firstname", p.getFirstname());
        itemPerson.put("lastname", p.getLastname());

        arrayPerson.add(itemPerson);

        json.put("person", arrayPerson);

        json.put("birthday", u.getBirthday() + "");
        json.put("password", u.getPassword());

        return json.toString().replace("[", "").replace("]", "");
    }

    /*============================================================================
        Talents
    ============================================================================*/

    public static String talentToJson(String name){
        JSONObject json = new JSONObject();

        json.put("name", name);

        return json.toString();
    }

    public static String userTalentToJson(Users_has_talents userTalents){
        JSONObject json = new JSONObject();

        json.put("talentId", userTalents.getTalentId());
        json.put("description", userTalents.getDescription());
        json.put("hide", userTalents.getHide());

        return json.toString().replace("[", "").replace("]", "");
    }
    /*============================================================================
        Suggestions
    ============================================================================*/
    public static String voteToJson(Votes vote){
        JSONObject json = new JSONObject();

        json.put("text", vote.getText());
        json.put("person_id", vote.getPerson_id());
        json.put("users_has_talents_talent_id", vote.getUsers_has_talents_talent_id());
        json.put("users_has_talents_person_id", vote.getUsers_has_talents_person_id());

        return json.toString();
    }
    public static String reactionToSuggestionToJson(long voteId, boolean accepted){
        JSONObject json = new JSONObject();

        json.put("voteId", voteId);
        json.put("accepted", accepted);

        return json.toString();
    }
    public static String reactionToSuggestionToJson(long voteId, boolean accepted, boolean hide){
        JSONObject json = new JSONObject();

        json.put("voteId", voteId);
        json.put("accepted", accepted);
        json.put("hide", hide);

        return json.toString();
    }
    /*============================================================================
        Endorsements
    ============================================================================*/
    public static String endorsementToJson(Endorsements endorsement){
        JSONObject json = new JSONObject();

        json.put("description", endorsement.getDescription());
        json.put("persons_id", endorsement.getPersons_id());
        json.put("users_has_talents_person_id", endorsement.getUsers_has_talents_person_id());
        if(endorsement.getUsers_has_talents_person_id()!=null) json.put("users_has_talents_talent_id", endorsement.getUsers_has_talents_talent_id());

        return json.toString();
    }
}
