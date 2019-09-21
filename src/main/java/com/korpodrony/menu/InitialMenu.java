package com.korpodrony.menu;

import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import com.korpodrony.service.PropertiesService;
import com.korpodrony.utils.JSONReader;

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
                try {
                    initialParametersLoad();
                } catch (IOException | ClassNotFoundException  e) {
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
            if (Files.exists(Paths.get(path, "Users"))) {
                Set<User> usersFromJson = new JSONReader().parseUserFromJSONFile(Paths.get(path, "Users"));
                org.setUsers(usersFromJson);
                User.setCurrentID(usersFromJson);
                System.out.println(User.getCurrentID());
            }
            if (Files.exists(Paths.get(path, "Activities"))) {
                Set<Activity> activtiesFromJson = new JSONReader().parseActivityFromJSONFile(Paths.get(path, "Activities"));
                org.setActivities(activtiesFromJson);
                Activity.setCurrentID(activtiesFromJson);
                System.out.println(Activity.getCurrentID());
            }
            if (Files.exists(Paths.get(path, "Plans"))) {
                Set<Plan> plansFromJson = new JSONReader().parsePlanFromJSONFile(Paths.get(path, "Plans"));
                org.setPlans(plansFromJson);
                Plan.setCurrentID(plansFromJson);
                System.out.println(Plan.getCurrentID());
            }
            new MainMenu(org).startMainMenu();
        }
    }
}