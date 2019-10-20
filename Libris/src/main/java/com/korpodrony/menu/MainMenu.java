package com.korpodrony.menu;


import com.korpodrony.model.Organization;
import com.korpodrony.service.OrganizationService;
import com.korpodrony.service.PropertiesService;
import com.korpodrony.utils.IoTools;
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
            int choice = IoTools.getIntFromUser();
            runMainMenuDecide(choice);
        } while (!exit);
            writeOrganizationToFile();
    }

    private void writeOrganizationToFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        if (path == null) {
            return;
        }
        JSONWriter.writeJSONToFile(Paths.get(path, "Users.json"), dB.getUsers());
        JSONWriter.writeJSONToFile(Paths.get(path, "Activities.json"), dB.getActivities());
        JSONWriter.writeJSONToFile(Paths.get(path, "Plans.json"), dB.getPlans());
    }

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
