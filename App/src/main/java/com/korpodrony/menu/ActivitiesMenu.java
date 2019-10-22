package com.korpodrony.menu;

import com.korpodrony.service.ActivitiesService;
import com.korpodrony.utils.IoTools;

public class ActivitiesMenu {
    private MainMenu mainMenu;
    private ActivitiesService aS = new ActivitiesService();

    void startActivitiesMenu() {
        do {
            Messages.printActivitiesMenu();
            int choice = IoTools.getIntFromUser();
            decide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private void decide(int choice) {
        switch (choice) {
            case 1: {
                addActivity();
                break;
            }
            case 2: {
                editActivity();
                break;
            }
            case 3: {
                deleteActivity();
                break;
            }
            case 4: {
                showActivity();
                break;
            }
            case 5: {
                assignUser();
                break;
            }
            case 6: {
                unassignUser();
                break;
            }
            case 7: {
                showAssignedUsers();
                break;
            }
            case 8: {
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }

    }

    private void showAssignedUsers() {
        System.out.println("-- Pokazywanie użytkowników przypisanych do zajęć --");
        aS.showAssignedUsers();
    }

    private void unassignUser() {
        System.out.println("-- Wypisywanie użytkownika z zajęć --");
        aS.unassignUserFromActivity();
    }

    private void assignUser() {
        System.out.println("-- Przypisywanie użytkownika do zajęć --");
        aS.assignUserToActivity();
    }

    private void addActivity() {
        System.out.println("-- Dodawanie nowych zajęć --");
       aS.addActivity();
    }

    private void editActivity() {
       aS.editActivity();
    }

    private void deleteActivity() {
        System.out.println("-- Usuwanie istniejących zajęć --");
        aS.removeActivity();
    }

    private void showActivity() {
        System.out.println("-- Pokazywanie istniejących zajęć --");
        aS.printActivities();
    }
}
