package com.project.talent1.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.talent1.Models.Persons;
import com.project.talent1.Models.Talents;
import com.project.talent1.Models.Users;
import com.project.talent1.Models.Users_has_talents;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class JsonHelper {
    public static Users getUserOutJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return mapper.convertValue(node.get("user"), Users.class);
    }
    public static Persons getPersonOutJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return mapper.convertValue(node.get("person"), Persons.class);
    }
    public static Talents getTalentOutJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return mapper.convertValue(node.get("talent"), Talents.class);
    }
    public static Users_has_talents getUserTalentOutJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return mapper.convertValue(node.get("userTalent"), Users_has_talents.class);
    }
    public static String getStringOutJson(String name,String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return mapper.convertValue(node.get(name), String.class);
    }
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
}
