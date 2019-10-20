package com.korpodrony.menu;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.User;
import com.korpodrony.utils.IoTools;

import java.util.List;
import java.util.stream.Collectors;

public class ActivitiesMenu {
    private MainMenu mainMenu;

    public ActivitiesMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

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
        mainMenu.dBService.showAssignedUsers();
    }

    private void unassignUser() {
        System.out.println("-- Wypisywanie użytkownika z zajęć --");
        mainMenu.dBService.unassignUserFromActivity();
    }

    private void assignUser() {
        System.out.println("-- Przypisywanie użytkownika do zajęć --");
        mainMenu.dBService.assignUserToActivity();
    }

    private void addActivity() {
        System.out.println("-- Dodawanie nowych zajęć --");
        mainMenu.dBService.addActivity();
    }

    private void editActivity() {
        mainMenu.dBService.editActivity();
    }

    private void deleteActivity() {
        System.out.println("-- Usuwanie istniejących zajęć --");
        mainMenu.dBService.removeActivity();
    }

    private void showActivity() {
        System.out.println("-- Pokazywanie istniejących zajęć --");
        mainMenu.dBService.printActivities();
    }
}
