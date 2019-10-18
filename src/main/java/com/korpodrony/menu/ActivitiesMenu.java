package com.korpodrony.menu;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.User;

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
        mainMenu.dBService.printActivities();
        if (mainMenu.dB.getAllActivities().isEmpty()) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID zajęć, których użytkowników chcesz obejrzeć:");
        if (!mainMenu.dB.hasActivityWithThisID(choice)) {
            System.out.println("Nie ma takich zajęć.");
            return;
        }
        List<User> users = mainMenu.dB.getActivity(choice).getAssignedUsersIDs().stream().map(x -> mainMenu.dB.getUser(x)).collect(Collectors.toList());
        if (users.isEmpty()) {
            System.out.println("Zajęcia nie mają przypisanych żadnych użytkowników!");
            return;
        }
        users.sort(new UserIDComparator());
        users.forEach(System.out::println);
    }

    private void unassignUser() {
        System.out.println("-- Wypisywanie użytkownika z zajęć --");
        mainMenu.dBService.unassingUserFromActivity();
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
