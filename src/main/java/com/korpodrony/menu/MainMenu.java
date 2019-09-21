package com.korpodrony.menu;


public class MainMenu {
    public static boolean exit;
    public static boolean contextMenuExit;
    public static boolean subMenuExit;
    public static int menuIdToSearch;

    public void startMainMenu() {
        initialParametersLoad();
        do {
            contextMenuExit = false;
            Messages.printMainMenu();
            int choice = IoTools.getUserInput();
            runMainMenuDecide(choice);
        } while (!exit);
// TODO discuss whether our app should save changes on the fly, or should it ask at the end of the program to write app state/config etd
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
//                roomsMenu(); TODO === to be implemented later
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
    }


}
