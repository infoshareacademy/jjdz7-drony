package com.korpodrony;

import com.korpodrony.model.Organization;
import com.korpodrony.model.User;
import com.korpodrony.service.OrganizationService;
import com.korpodrony.util.Json;

import java.io.IOException;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Organization org = new Organization();
        OrganizationService os = new OrganizationService(org);
        org.createUser("Jan", "Sam");
        org.createUser("Xan", "Sam");
        org.createUser("Han", "Sam");
        org.createUser("Pan", "Sam");
        Json.writeSetToFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users", org.users);
        Set<User> users = null;
        try {
            users = Json.readSetFromFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users", User.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(users);
    }
}