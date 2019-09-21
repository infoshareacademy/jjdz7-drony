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
        String activityName = IoTools.readStringInputWithMessage("Podaj nazwę zajęć:");
        int maxUsers = IoTools.readIntInputWithMessage("Podaj maksymalną ilość użytkowników:");
        int duration = IoTools.readIntInputWithMessage("Podaj długość zajęć (w kwadransach):");
        //TODO method call to be added
    }

    private static void startActivitiesMenuEditActivity() {
        System.out.println("Edytowanie istniejących zajęć");
        int idToRetrieve= IoTools.readIntInputWithMessage("Podaj ID zajęć:");
        String activityName = IoTools.readStringInputWithMessage("Podaj nazwę zajęć:");
        int maxUsers = IoTools.readIntInputWithMessage("Podaj maksymalną ilość użytkowników:");
        int duration = IoTools.readIntInputWithMessage("Podaj długość zajęć (w kwadransach):");
        //TODO method call to be added
    }

    private static void startActivitiesMenuDeleteActivity() {
        System.out.println("Usuwanie istniejących zajęć");
        int idToDelete= IoTools.readIntInputWithMessage("Podaj ID zajęć:");
        //TODO show existing activity
        String confirmation = IoTools.readStringInputWithMessage("Czy na pewno Y/N?");
        if (confirmation.equals("Y")) {
            System.out.println("Potwierdziłeś!");
            //TODO method or direct delete using arrayservice?
        }
    }

    private static void startActivitiesMenuShowActivity() {
        System.out.println("Pokazywanie istniejących zajęć");
        int idToRetrieve= IoTools.readIntInputWithMessage("Podaj ID zajęć:");
        //TODO method to show activities
    }
}
