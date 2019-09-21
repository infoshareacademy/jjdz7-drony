package com.korpodrony.menu;


import com.korpodrony.model.Organization;
import com.korpodrony.service.OrganizationService;
import com.korpodrony.service.PropertiesService;
import com.korpodrony.utils.JSONWriter;

import java.nio.file.Paths;

public class MainMenu {
    public static boolean exit;
    public static boolean contextMenuExit;
    public static boolean subMenuExit;
    public static int menuIdToSearch;

    public  Organization dB;
    public  OrganizationService dBService;

    public MainMenu(Organization dB) {
        this.dB = dB;
        this.dBService = new OrganizationService(dB);
    }

    public void startMainMenu() {
        do {
            contextMenuExit = false;
            Messages.printMainMenu();
            int choice = IoTools.getUserInput();
            runMainMenuDecide(choice);
        } while (!exit);
            writeOrganizationToFile();
    }

    private void writeOrganizationToFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        if (path == null) {
            path = "/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/";
        }
        JSONWriter.writeJSONToFile(Paths.get(path, "Users"), dB.getUsers());
        JSONWriter.writeJSONToFile(Paths.get(path, "Activities"), dB.getActivities());
        JSONWriter.writeJSONToFile(Paths.get(path, "Plans"), dB.getPlans());
    }

    /**
     * Menu choice logic
     **/

    private void runMainMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                new SearchMenu(this).startSearchMenu();
                break;
            }
            case 2: {
                new UsersMenu(this).startUsersMenu();
                break;
            }
//            case 3: {
////                       TODO === to be implemented later
//                Messages.printFeatureNotImplementedYet();
//                break;
//            }
            case 3: {
                new ActivitiesMenu(this).StartActivitiesMenu();
                break;
            }
            case 4: {
                 new SchedulesMenu(this).startSchedulesMenu();
                break;
            }
            case 5: {
                exit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }
        }
    }
}
