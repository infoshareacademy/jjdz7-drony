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
            Messages.printUserMenu();
            int choice = IoTools.getIntFromUser();
            runUsersMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private void runUsersMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startUsersMenuAddUser();
                break;
            }
            case 2: {
                startUsersMenuEditUser();
                break;
            }
            case 3: {
                startUsersMenuDeleteUser();
                break;
            }
            case 4: {
                startUsersMenuShowUser();
                break;
            }
            case 5: {
                startUsersMenuShowUserActivities();
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

    private void startUsersMenuShowUserActivities() {
        System.out.println("-- Pokazywanie zajęć użytkownika --");
        mainMenu.dBService.printUsers();
        if (mainMenu.dB.getAllUsers().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika do którego zajęcia chcesz obejrzeć:");
        if (!mainMenu.dB.hasUserWithThisID(choice)){
            System.out.println("Nie ma takiego użytkownika.");
            return;
        }
        List<Activity> userActivities = mainMenu.dB.getAllActivies().stream().filter(x -> x.getAssignedUsersIDs().contains(choice)).collect(Collectors.toList());
        if (userActivities.isEmpty()) {
            System.out.println("Użytkownik nie jest przypisany do żadnych zajęć!");
            return;
        }
        userActivities.sort(new ActivityIDComparator());
        userActivities.forEach(System.out::println);
    }

    private void startUsersMenuAddUser() {
        System.out.println("-- Dodawanie nowego użytkownika --");
        mainMenu.dBService.addUser();
    }

    private void startUsersMenuEditUser() {
        System.out.println("-- Edytowanie użytkownika --");
        mainMenu.dBService.editUser();
    }

    private void startUsersMenuDeleteUser() {
        System.out.println("-- Usuwanie użytkownika --");
        mainMenu.dBService.removeUser();
    }

    private void startUsersMenuShowUser() {
        mainMenu.dBService.printUsers();
    }
}
