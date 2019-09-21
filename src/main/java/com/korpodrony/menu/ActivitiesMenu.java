package com.korpodrony.menu;

public class ActivitiesMenu {
    static void StartActivitiesMenu() {
        do {
            Messages.printActivitiesMenu("Zajęcia");
            int choice = IoTools.getUserInput();
            runActivitiesMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private static void runActivitiesMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startActivitiesMenuAddActivity();
                break;
            }
            case 2: {
                startActivitiesMenuEditActivity();
                break;
            }
            case 3: {
                startActivitiesMenuDeleteActivity();
                break;
            }
            case 4: {
                startActivitiesMenuShowActivity();
                break;
            }
            case 5: {
                startActivitiesMenuAssignUser();
                break;
            }
            case 6: {
                startActivitiesMenuUnassignUser();
                break;
            }
            case 7: {
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }

    }

    private static void startActivitiesMenuUnassignUser() {
        System.out.println("Wypisywanie użytkownika z zajęć");
        MainMenu.dBService.unassingUserFromActivity();
    }

    private static void startActivitiesMenuAssignUser() {
        System.out.println("Przypisywanie użytkownika do zajęć");
        MainMenu.dBService.assignUserToActivity();
    }

    private static void startActivitiesMenuAddActivity() {
        System.out.println("Dodawanie nowych zajęć");
        MainMenu.dBService.addActivity();
    }

    private static void startActivitiesMenuEditActivity() {
        MainMenu.dBService.editActiity();
    }

    private static void startActivitiesMenuDeleteActivity() {
        System.out.println("Usuwanie istniejących zajęć");
        MainMenu.dBService.removeActivity();
    }

    private static void startActivitiesMenuShowActivity() {
        System.out.println("Pokazywanie istniejących zajęć");
        MainMenu.dBService.printActivites();
    }
}
