package com.korpodrony.menu;

public class UsersMenu {
    static void startUsersMenu() {
        do {
            Messages.printUsersContextMenu();
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
                startUsersMenuListUsers();
                break;
            }
            case 5: {
                startUsersMenuAssignUserToActivity();
                break;
            }
            case 6: {
                startUsersMenuUnassignUserToActivity();
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


    private static void startUsersMenuListUsers() {
        MainMenu.dBService.printUsers();
    }

    private static void startUsersMenuAssignUserToActivity() {
        System.out.println("Przydzielanie użytkownika do zajęć");
        MainMenu.dBService.assignUserToActivity();
    }

    private static void startUsersMenuUnassignUserToActivity(){
        System.out.println("Usuwanie użytkownika z zajęć");
        MainMenu.dBService.unassingUserFromActivity();
    }

}
