package com.project.talent1;

import com.project.talent1.Models.Persons;
import com.project.talent1.Models.Users;
import com.project.talent1.Models.Users_has_talents;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class TestHelper {
    public static String loginCredentialsToJson(String email, String password){
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("password", password);
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
        itemUser.put("birthday", u.getBirthday().toString());
        itemUser.put("password", u.getPassword());
        arrayUser.add(itemUser);

        json.put("user", arrayUser);

        String test = json.toString();

        return json.toString().replace("[", "").replace("]", "");
    }
    public static String talentToJson(String name){
        JSONObject json = new JSONObject();

        json.put("name", name);

        return json.toString();
    }
    public static String userTalentToJson(Users_has_talents userTalents){
        JSONObject json = new JSONObject();

        JSONArray arrayUserTalents = new JSONArray();
        JSONObject itemUserTalent = new JSONObject();
        itemUserTalent.put("talentId", userTalents.getTalentId());
        itemUserTalent.put("description", userTalents.getDescription());
        itemUserTalent.put("hide", userTalents.getHide());
        arrayUserTalents.add(itemUserTalent);

        json.put("userTalent", arrayUserTalents);

        return json.toString().replace("[", "").replace("]", "");
    }
}
