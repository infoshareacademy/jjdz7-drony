package com.korpodrony.menu;


import com.korpodrony.service.RepositoryService;
import com.korpodrony.utils.IoTools;

public class MainMenu {
    public static boolean exit;
    public static boolean contextMenuExit;
    public static boolean subMenuExit;
    public static int menuIdToSearch;

    public void startMainMenu() {
        do {
            contextMenuExit = false;
            Messages.printMainMenu();
            int choice = IoTools.getIntFromUser();
            runMainMenuDecide(choice);
        } while (!exit);
            new RepositoryService().writeRepositoryToFile();
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
                new ActivitiesMenu(this).startActivitiesMenu();
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
