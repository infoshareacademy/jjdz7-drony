package com.korpodrony.menu;


import com.korpodrony.model.Organization;
import com.korpodrony.service.OrganizationService;

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
