package com.korpodrony.menu;

import com.korpodrony.service.UserService;
import com.korpodrony.utils.IoTools;

public class UsersMenu {
    private UserService uS = new UserService();

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
        uS.showUserActivities();

    }

    private void addUser() {
        System.out.println("-- Dodawanie nowego użytkownika --");
        uS.addUser();
    }

    private void editUser() {
        System.out.println("-- Edytowanie użytkownika --");
        uS.editUser();
    }

    private void deleteUser() {
        System.out.println("-- Usuwanie użytkownika --");
        uS.removeUser();
    }

    private void showUser() {
        uS.printUsers();
    }
}
