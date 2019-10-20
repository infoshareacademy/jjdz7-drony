package com.korpodrony.menu;

import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import com.korpodrony.service.OrganizationService;
import com.korpodrony.service.PropertiesService;
import com.korpodrony.utils.IoTools;
import com.korpodrony.utils.JSONReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class InitialMenu {

    public void startInitialMenu() {
        int choice = 0;
        do {
            Messages.printInitialMenu();
            choice = IoTools.getIntFromUser();
            decide(choice);
        } while (choice != 1 && choice != 2);
    }

    private void decide(int choice) {
        switch (choice) {
            case 1: {
                initialParametersLoad();
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

    private void initialParametersLoad() {
            new MainMenu(OrganizationService.loadParametersFromFile()).startMainMenu();
        }

}