package com.korpodrony.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import com.korpodrony.service.PropertiesService;
import com.korpodrony.utils.JSONReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                try {
                    initialParametersLoad();
                } catch (IOException | ClassNotFoundException e) {
                    createNewOrganization();
                }
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
            ObjectMapper objectMapper = new ObjectMapper();
            if (Files.exists(Paths.get(path, "Users"))) {
                Set<User> usersFromJson = new JSONReader().parseUserFromJSONFile(Paths.get("/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/Users"));
                org.setUsers(usersFromJson);
            }
            if (Files.exists(Paths.get(path, "Activities"))) {
                Set<Activity> activtiesFromJson = new JSONReader().parseActivityFromJSONFile(Paths.get(path, "Activities"));
                org.setActivities(activtiesFromJson);
            }
            if (Files.exists(Paths.get(path, "Plans"))) {
                Set<Plan> plansFromJson = new JSONReader().parsePlanFromJSONFile(Paths.get(path, "Plans"));
                org.setPlans(plansFromJson);
            }
            new MainMenu(org).startMainMenu();
        }
    }
}