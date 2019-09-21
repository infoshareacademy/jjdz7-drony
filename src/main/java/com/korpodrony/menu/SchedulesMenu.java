package com.korpodrony.menu;

public class SchedulesMenu {
    static void startSchedulesMenu() {
        do {
            Messages.printContextMenu("Plany");
            int choice = IoTools.getUserInput();
            runSchedulesMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private static void runSchedulesMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startSchedulesMenuAddSchedule();
                break;
            }
            case 2: {
                startSchedulesMenuEditSchedule();
                break;
            }
            case 3: {
                startSchedulesMenuDeleteSchedule();
                break;
            }
            case 4: {
                startSchedulesMenuShowSchedule();
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

    private static void startSchedulesMenuAddSchedule() {
        System.out.println("Dodawanie nowych planów");
        String scheduleName= IoTools.readStringInputWithMessage("Podaj nazwę planu");
        //TODO call method adding schedule
    }

    private static void startSchedulesMenuEditSchedule() {
        System.out.println("Edytowanie istniejących planów");
        int idToEdit= IoTools.readIntInputWithMessage("Podaj ID planu do edycji:");
        //TODO check if ID exists
        String scheduleName= IoTools.readStringInputWithMessage("Podaj nazwę planu");
        //TODO call method editing schedule
    }

    private static void startSchedulesMenuDeleteSchedule() {
        System.out.println("Usuwanie istniejących planów");
        int idToDelete= IoTools.readIntInputWithMessage("Podaj ID planu do edycji:");
        //TODO check if ID exists
        String confirmation = IoTools.readStringInputWithMessage("Czy na pewno Y/N?");
        if (confirmation.equals("Y")) {
            System.out.println("Potwierdziłeś!");
            //TODO method or direct delete using arrayservice?
        }

    }

    private static void startSchedulesMenuShowSchedule() {
        System.out.println("Pokazywanie istniejących planów");
        int idToDelete= IoTools.readIntInputWithMessage("Podaj ID planu do edycji:");
        //TODO method to show existing schedules
    }
}
