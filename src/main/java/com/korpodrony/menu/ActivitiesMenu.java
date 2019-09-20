package com.korpodrony.menu;

public class ActivitiesMenu {
    static void StartActivitiesMenu() {
        do {
            Messages.printContextMenu("Zajęcia");
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
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }

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
        int idToShow=IoTools.readIntInputWithMessage("Podaj ID zajęć");
        System.out.println(MainMenu.dB.getActivity(idToShow).toString());
    }
}
