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
        MainMenu.dBService.addPlan();
    }

    private static void startSchedulesMenuEditSchedule() {
        System.out.println("Edytowanie istniejących planów");
        MainMenu.dBService.editPlan();
    }

    private static void startSchedulesMenuDeleteSchedule() {
        System.out.println("Usuwanie istniejących planów");
        MainMenu.dBService.removePlan();
    }

    private static void startSchedulesMenuShowSchedule() {
        System.out.println("Pokazywanie istniejących planów");
        MainMenu.dBService.printPlans();
    }
}
