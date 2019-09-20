package com.korpodrony.menu;

import com.korpodrony.model.User;

public class UsersMenu {
    static void startUsersMenu() {
        do {
            Messages.printContextMenu("Użytkownicy");
            int choice = IoTools.getUserInput();
            runUsersMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private static void runUsersMenuDecide(int choice) {
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
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }

    }

    private static void startUsersMenuAddUser() {
        System.out.println("Dodawanie nowego użytkownika");
        MainMenu.dBService.addUser();
    }

    private static void startUsersMenuEditUser() {
        System.out.println("Edytowanie użytkownika");
        MainMenu.dBService.editUser();
    }

    private static void startUsersMenuDeleteUser() {
        System.out.println("Usuwanie użytkownika");
        MainMenu.dBService.removeUser();
        }


    private static void startUsersMenuShowUser() {
        System.out.println("Pokazywanie użytkownika po ID:");
        int idToShow=IoTools.readIntInputWithMessage("Podaj ID użytkownika");
        System.out.println(MainMenu.dB.getUser(idToShow).toString());
    }
}
