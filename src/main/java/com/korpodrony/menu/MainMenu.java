package com.korpodrony.menu;


import com.korpodrony.model.Organization;
import com.korpodrony.service.OrganizationService;

public class MainMenu {
    public static boolean exit;
    public static boolean contextMenuExit;
    public static boolean subMenuExit;
    public static int menuIdToSearch;

    public static Organization dB = new Organization();
    public static OrganizationService dBService = new OrganizationService(dB);

    public void startMainMenu() {
        initialParametersLoad();
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
                SearchMenu.startSearchMenu();
                break;
            }
            case 2: {
                UsersMenu.startUsersMenu();
                break;
            }
            case 3: {
//                       TODO === to be implemented later
                Messages.printFeatureNotImplementedYet();
                break;
            }
            case 4: {
                ActivitiesMenu.StartActivitiesMenu();
                break;
            }
            case 5: {
                SchedulesMenu.startSchedulesMenu();
                break;
            }
            case 6: {
                exit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }
    }
    private void initialParametersLoad() {
        System.out.println("=== Parameters load placeholder ===");
    }


}
