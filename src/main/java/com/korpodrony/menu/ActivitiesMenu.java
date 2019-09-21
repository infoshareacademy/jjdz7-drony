package com.korpodrony.menu;

public class ActivitiesMenu {
    static void StartActivitiesMenu() {
        do {
            Messages.printActivitiesContextMenu();
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
                startActivitiesMenuListActivities();
                break;
            }
            case 5: {
                startActivitiesMenuAssignActivityToSchedule();
                break;
            }
            case 6: {
                startActivitiesMenuUnssignActivityToSchedule();
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

    private static void startActivitiesMenuAddActivity() {
        System.out.println("Dodawanie nowych zajęć");
        MainMenu.dBService.addActivity();
    }

    private static void startActivitiesMenuEditActivity() {
        MainMenu.dBService.editActivity();
    }

    private static void startActivitiesMenuDeleteActivity() {
        System.out.println("Usuwanie istniejących zajęć");
        MainMenu.dBService.removeActivity();
    }

    private static void startActivitiesMenuListActivities() {
        MainMenu.dBService.printActivites();
    }

    private static void startActivitiesMenuAssignActivityToSchedule(){
        System.out.println("Przydzielanie zajęć do planów");
        MainMenu.dBService.assignActivityToPlan();
    }

    private static void startActivitiesMenuUnssignActivityToSchedule(){
        System.out.println("Usuwanie zajęć z planów");
        MainMenu.dBService.unassignActivityFromPlan();
    }
}
