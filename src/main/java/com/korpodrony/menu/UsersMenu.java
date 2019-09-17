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
        String userName = IoTools.readStringInputWithMessage("Podaj imię");
        String userSurname = IoTools.readStringInputWithMessage("Podaj nazwisko");
        String userBirthdate = IoTools.readStringInputWithMessage("Podaj datę urodzenia w formacie DDMMYYYY"); //TODO add validator
        String userPassword = IoTools.readStringInputWithMessage("Podaj hasło");
        User usertoAdd = new User(userName, userSurname, userBirthdate, userPassword);
        //TODO method or direct add using arrayservice?
    }

    private static void startUsersMenuEditUser() {
        System.out.println("Edytowanie użytkownika");
        IoTools.readIntInputWithMessage("Podaj ID użytkownika do edycji:");
        //TODO check if ID exist
        String userName = IoTools.readStringInputWithMessage("Podaj imię");
        String userSurname = IoTools.readStringInputWithMessage("Podaj nazwisko");
        String userBirthdate = IoTools.readStringInputWithMessage("Podaj datę urodzenia w formacie DDMMYYYY"); //TODO add validator
        String userPassword = IoTools.readStringInputWithMessage("Podaj hasło");
        //insertExisitngUser?(userName, userSurname, userBirthdate, userPassword); TODO call method to alter existing element
    }

    private static void startUsersMenuDeleteUser() {
        System.out.println("Usuwanie użytkownika");
        IoTools.readIntInputWithMessage("Podaj ID użytkownika do usunięcia:");
        //TODO check if ID exist
        String confirmation = IoTools.readStringInputWithMessage("Czy na pewno Y/N?");
        if (confirmation.equals("Y")) {
            System.out.println("Potwierdziłeś!");
            //TODO method or direct delete using arrayservice?
        }
    }

    private static void startUsersMenuShowUser() {
        System.out.println("Pokazywanie użytkownika po ID:");
        int idToShow= IoTools.readIntInputWithMessage("Podaj ID użytkownika");
        //TODO method to show existing user by ID
    }
}
