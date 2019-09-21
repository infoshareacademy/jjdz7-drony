package com.korpodrony;

import com.korpodrony.model.Organization;
import com.korpodrony.model.User;
import com.korpodrony.service.OrganizationService;
import com.korpodrony.service.PropertiesService;
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
        org.createUser("Jan", "Brzechwa");
        org.createUser("Kubuś", "Puchatek");
        org.createUser("Han", "Solo");
        org.createUser("Pan", "Paderweski");
        org.createActivity("Java", (short) 30, (byte) 5);
        org.assignUserToActivity(1,1);
        org.getActivity(1);
        Json.writeSetToFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Actvities", org.getUsers());
        try {
            org.setUsers(Json.readSetFromFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users", User.class));
        } catch (ClassNotFoundException | IOException e){
            System.out.println("sdadsadas");
        }
//        PropertiesService propertiesService = new PropertiesService();
//        String path =   propertiesService.getProperty(PropertiesService.APP_PATH);
//        System.out.println(path);
    }
}