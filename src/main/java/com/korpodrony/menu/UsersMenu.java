package com.korpodrony.menu;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.model.Activity;

import java.util.List;
import java.util.stream.Collectors;

public class UsersMenu {
    MainMenu mainMenu;

    public UsersMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void startUsersMenu() {
        do {
            Messages.printUsersMenu();
            int choice = IoTools.getIntFromUser();
            decide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private void decide(int choice) {
        switch (choice) {
            case 1: {
                addUser();
                break;
            }
            case 2: {
                editUser();
                break;
            }
            case 3: {
                deleteUser();
                break;
            }
            case 4: {
                showUser();
                break;
            }
            case 5: {
                showUserActivities();
                break;
            }
            case 6: {
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }

    }

    private void showUserActivities() {
        System.out.println("-- Pokazywanie zajęć użytkownika --");
        mainMenu.dBService.printUsers();
        if (mainMenu.dB.getAllUsers().isEmpty()) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID użytkownika, do którego zajęcia chcesz obejrzeć:");
        if (!mainMenu.dB.hasUserWithThisID(choice)){
            System.out.println("Nie ma takiego użytkownika.");
            return;
        }
        List<Activity> userActivities = mainMenu.dB.getAllActivities().stream()
                .filter(x -> x.getAssignedUsersIDs().contains(choice))
                .collect(Collectors.toList());
        if (userActivities.isEmpty()) {
            System.out.println("Użytkownik nie jest przypisany do żadnych zajęć.");
            return;
        }
        userActivities.sort(new ActivityIDComparator());
        userActivities.forEach(System.out::println);
    }

    private void addUser() {
        System.out.println("-- Dodawanie nowego użytkownika --");
        mainMenu.dBService.addUser();
    }

    private void editUser() {
        System.out.println("-- Edytowanie użytkownika --");
        mainMenu.dBService.editUser();
    }

    private void deleteUser() {
        System.out.println("-- Usuwanie użytkownika --");
        mainMenu.dBService.removeUser();
    }

    private void showUser() {
        mainMenu.dBService.printUsers();
    }
}