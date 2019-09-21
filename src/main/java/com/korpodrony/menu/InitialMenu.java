package com.korpodrony.menu;

import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.User;
import com.korpodrony.service.PropertiesService;
import com.korpodrony.util.Json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class InitialMenu {

    public void startInitialMenu() {
        int choice = 0;
        do {
            Messages.printInitialMenu();
            choice = IoTools.getUserInput();
            runInitialMenuDecide(choice);
        } while (choice != 1 && choice != 2);
    }

    private void runInitialMenuDecide(int choice) {
        switch (choice) {
            case 1: {
//                initialParametersLoad();
                break;
            }
            case 2: {
                createNewOrganization();
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }
    }

    private void createNewOrganization() {
        Organization org = new Organization();
        new MainMenu(org).startMainMenu();
    }

    private void initialParametersLoad() throws IOException, ClassNotFoundException {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        if (path == null) {
            createNewOrganization();
        } else {
            Organization org = new Organization();
            Set<User>users = new HashSet<>();
            Set<User>activities = new HashSet<>();
            Set<User>plans = new HashSet<>();
            if (Files.exists(Paths.get(path,"Users"))){
                org.setUsers(Json.readSetFromFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users", User.class));
            }
            if (Files.exists(Paths.get(path,"Activities"))){
                org.setActivities(Json.readSetFromFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users", Activity.class));
            }
            if (Files.exists(Paths.get(path,"Users"))){
                org.setUsers(Json.readSetFromFile("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users", Activity.class));
            }
        }
    }
}