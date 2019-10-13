package com.korpodrony.menu;

public class SearchMenu {


    static void startSearchMenu() {

        do {
            Messages.printSearchMenu();
            int choice = IoTools.getIntegerWithMessage("\nTwój wybór: ");
            runSearchMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private static void runSearchMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startSearchByUserMenu();
                break;
            }
            case 2: {
                Messages.printFeatureNotImplementedYet();
                //searchByRoomMenu TODO === to be implemented later
                break;
            }
            case 3: {
                startSearchByActivityMenu();
                break;
            }
            case 4: {
                startSearchByScheduleMenu();
                break;
            }
            case 5: {
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }

    }

    private static void startSearchByUserMenu() {
        System.out.println("Szukanie użytkownika po ID, wpisz ID");
        MainMenu.menuIdToSearch = IoTools.getIntegerWithMessage("Wpisz szukane id: ");
        System.out.println("Szukam po ID " + MainMenu.menuIdToSearch); //TODO insert correct method call
    }

    private static void startSearchByActivityMenu() {
        System.out.println("Szukanie zajęć po ID, wpisz ID");
        MainMenu.menuIdToSearch = IoTools.getIntegerWithMessage("Wpisz szukane id: ");
        System.out.println("Szukam po ID " + MainMenu.menuIdToSearch); //TODO insert correct method call
    }

    private static void startSearchByScheduleMenu() {
        System.out.println("Szukanie planu po ID, wpisz ID");
        MainMenu.menuIdToSearch = IoTools.getIntegerWithMessage("Wpisz szukane id: ");
        System.out.println("Szukam po ID " + MainMenu.menuIdToSearch); //TODO insert correct method call
    }
}
