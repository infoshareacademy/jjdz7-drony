package com.korpodrony.menu;


import com.korpodrony.model.User;

import java.util.Scanner;

public class Menu {
    private boolean exit;
    private boolean contextMenuExit;
    private boolean subMenuExit;

    private int idToSearch;

    public void startMainMenu() {
        initialParametersLoad();
        do {
            contextMenuExit = false;
            printMainMenu();
            int choice = getUserInput();
            runMainMenuDecide(choice);
        } while (!exit);
// TODO discuss whether our app should save changes on the fly, or should it ask at the end of the program to write app state/config etd
    }

    /**
     * Menu choice logic
     **/

    private void runMainMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startSearchMenu();
                break;
            }
            case 2: {
                startUsersMenu();
                break;
            }
            case 3: {
//                roomsMenu(); TODO === to be implemented later
                printFeatureNotImplementedYet();
                break;
            }
            case 4: {
                StartActivitiesMenu();
                break;
            }
            case 5: {
                startSchedulesMenu();
                break;
            }
            case 6: {
                exit = true;
                break;
            }
            default: {
                printBadInputErrorMessage();
            }

        }
    }

    private void startSearchMenu() {

        do {
            printSearchMenu();
            int choice = getUserInput();
            runSearchMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void runSearchMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startSearchByUserMenu();
                break;
            }
            case 2: {
                printFeatureNotImplementedYet();
                //searchByRoomMenu TODO === to be implemented later
                break;
            }
            case 3: {
                startSearchByActivityMenu();
                break;
            }
            case 4: {
                startSearchByScheduleMenu();
                break;
            }
            case 5: {
                contextMenuExit = true;
                break;
            }
            default: {
                printBadInputErrorMessage();
            }

        }

    }

    private void startSearchByUserMenu() {
        System.out.println("Szukanie użytkownika po ID, wpisz ID");
        idToSearch = getUserInput();
        System.out.println("Szukam po ID " + idToSearch); //TODO insert correct method call
    }

//    private void searchByRoomMenu(){} //TODO will be implemented when we add rooms fuctionality

    private void startSearchByActivityMenu() {
        System.out.println("Szukanie zajęć po ID, wpisz ID");
        idToSearch = getUserInput();
        System.out.println("Szukam po ID " + idToSearch); //TODO insert correct method call
    }

    private void startSearchByScheduleMenu() {
        System.out.println("Szukanie planu po ID, wpisz ID");
        idToSearch = getUserInput();
        System.out.println("Szukam po ID " + idToSearch); //TODO insert correct method call
    }

    private void startUsersMenu() {
        do {
            printContextMenu("Użytkownicy");
            int choice = getUserInput();
            runUsersMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void runUsersMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startUsersMenuAddUser();
                break;
            }
            case 2: {
                usersMenuEditUser();
                break;
            }
            case 3: {
                usersMenuDeleteUser();
                break;
            }
            case 4: {
                usersMenuShowUser();
                break;
            }
            case 5: {
                contextMenuExit = true;
                break;
            }
            default: {
                printBadInputErrorMessage();
            }

        }

    }

    private void startUsersMenuAddUser() {
        System.out.println("Dodawanie nowego użytkownika");
        String userName = readStringInputWithMessage("Podaj imię");
        String userSurname = readStringInputWithMessage("Podaj nazwisko");
        String userBirthdate = readStringInputWithMessage("Podaj datę urodzenia w formacie DDMMYYYY"); //TODO add validator
        String userPassword = readStringInputWithMessage("Podaj hasło");
        User usertoAdd = new User(userName, userSurname, userBirthdate, userPassword);
        //TODO method or direct add using arrayservice?
    }

    private void usersMenuEditUser() {
        System.out.println("Edytowanie użytkownika");
        readIntInputWithMessage("Podaj ID użytkownika do edycji:");
        //TODO check if ID exist
        String userName = readStringInputWithMessage("Podaj imię");
        String userSurname = readStringInputWithMessage("Podaj nazwisko");
        String userBirthdate = readStringInputWithMessage("Podaj datę urodzenia w formacie DDMMYYYY"); //TODO add validator
        String userPassword = readStringInputWithMessage("Podaj hasło");
        //insertExisitngUser?(userName, userSurname, userBirthdate, userPassword); TODO call method to alter existing element
    }

    private void usersMenuDeleteUser() {
        System.out.println("Usuwanie użytkownika");
        readIntInputWithMessage("Podaj ID użytkownika do usunięcia:");
        //TODO check if ID exist
        String confirmation = readStringInputWithMessage("Czy na pewno Y/N?");
        if (confirmation.equals("Y")) {
            System.out.println("Potwierdziłeś!");
            //TODO method or direct delete using arrayservice?
        }
    }

    private void usersMenuShowUser() {
        System.out.println("Pokazywanie użytkownika po ID:");
        int idToShow=readIntInputWithMessage("Podaj ID użytkownika");
        //TODO method to show existing user by ID
    }

    private void StartActivitiesMenu() {
        do {
            printContextMenu("Zajęcia");
            int choice = getUserInput();
            activitiesMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void activitiesMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                activitiesMenuAddActivity();
                break;
            }
            case 2: {
                activitiesMenuEditActivity();
                break;
            }
            case 3: {
                activitiesMenuDeleteActivity();
                break;
            }
            case 4: {
                activitiesMenuShowActivity();
                break;
            }
            case 5: {
                contextMenuExit = true;
                break;
            }
            default: {
                printBadInputErrorMessage();
            }

        }

    }

    private void activitiesMenuAddActivity() {
        System.out.println("Dodawanie nowych zajęć");
        String activityName = readStringInputWithMessage("Podaj nazwę zajęć:");
        int maxUsers = readIntInputWithMessage("Podaj maksymalną ilość użytkowników:");
        int duration = readIntInputWithMessage("Podaj długość zajęć (w kwadransach):");
        //TODO method call to be added
    }

    private void activitiesMenuEditActivity() {
        System.out.println("Edytowanie istniejących zajęć");
        int idToRetrieve=readIntInputWithMessage("Podaj ID zajęć:");
        String activityName = readStringInputWithMessage("Podaj nazwę zajęć:");
        int maxUsers = readIntInputWithMessage("Podaj maksymalną ilość użytkowników:");
        int duration = readIntInputWithMessage("Podaj długość zajęć (w kwadransach):");
        //TODO method call to be added
    }

    private void activitiesMenuDeleteActivity() {
        System.out.println("Usuwanie istniejących zajęć");
        int idToDelete=readIntInputWithMessage("Podaj ID zajęć:");
        //TODO show existing activity
        String confirmation = readStringInputWithMessage("Czy na pewno Y/N?");
        if (confirmation.equals("Y")) {
            System.out.println("Potwierdziłeś!");
            //TODO method or direct delete using arrayservice?
        }
    }

    private void activitiesMenuShowActivity() {
        System.out.println("Pokazywanie istniejących zajęć");
        int idToRetrieve=readIntInputWithMessage("Podaj ID zajęć:");
        //TODO method to show activities
    }

    private void startSchedulesMenu() {
        do {
            printContextMenu("Plany");
            int choice = getUserInput();
            schedulesMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void schedulesMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                schedulesMenuAddSchedule();
                break;
            }
            case 2: {
                schedulesMenuEditSchedule();
                break;
            }
            case 3: {
                schedulesMenuDeleteSchedule();
                break;
            }
            case 4: {
                schedulesMenuShowSchedule();
                break;
            }
            case 5: {
                contextMenuExit = true;
                break;
            }
            default: {
                printBadInputErrorMessage();
            }

        }

    }

    private void schedulesMenuAddSchedule() {
        System.out.println("Dodawanie nowych planów");
        String scheduleName=readStringInputWithMessage("Podaj nazwę planu");
        //TODO call method adding schedule
    }

    private void schedulesMenuEditSchedule() {
        System.out.println("Edytowanie istniejących planów");
        int idToEdit=readIntInputWithMessage("Podaj ID planu do edycji:");
        //TODO check if ID exists
        String scheduleName=readStringInputWithMessage("Podaj nazwę planu");
        //TODO call method editing schedule
    }

    private void schedulesMenuDeleteSchedule() {
        System.out.println("Usuwanie istniejących planów");
        int idToDelete=readIntInputWithMessage("Podaj ID planu do edycji:");
        //TODO check if ID exists
        String confirmation = readStringInputWithMessage("Czy na pewno Y/N?");
        if (confirmation.equals("Y")) {
            System.out.println("Potwierdziłeś!");
            //TODO method or direct delete using arrayservice?
        }

    }

    private void schedulesMenuShowSchedule() {
        System.out.println("Pokazywanie istniejących planów");
        int idToDelete=readIntInputWithMessage("Podaj ID planu do edycji:");
        //TODO method to show existing schedules
    }


    /**
     * Menu prints section
     **/

    private void printMainMenu() {
        System.out.println("\n1. Szukaj");
        System.out.println("2.Użytkownicy");
        System.out.println("3. Sale");
        System.out.println("4. Zajęcia");
        System.out.println("5. Plany");
        System.out.println("6. Wyjście");
    }

    private void printSearchMenu() {
        System.out.println("\n1. Szukaj po nazwie użytkownika");
        System.out.println("2. Szukaj po nazwie sali");
        System.out.println("3. Szukaj po nazwie zajęć");
        System.out.println("4. Szukaj po nazwie planu");
        System.out.println("5. Wyjście");
    }

    private void printContextMenu(String contextMenuType) {
        System.out.println("Menu --" + contextMenuType + "--");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż");
        System.out.println("5. Wyjście");
    }

    private void printBadInputErrorMessage() {
        System.out.println("Złe dane, dokonaj ponownego wyboru");
    }

    private void printFeatureNotImplementedYet() {
        System.out.println("Feature będzie wprowadzony w przyszłości");
    }

    /**
     * placeholder
     */

    /**
     * UTIL PART OF THE CLASS.
     * <p>
     * TODO - decide if this should be split to separate file
     */


    private int getUserInput() {
        System.out.print("\nTwój wybór: ");
        return Integer.parseInt(readStringUserInput());
    }

    public static String readStringUserInput() {
        return new Scanner(System.in).nextLine();
    }

    public static int readIntUserInput() {
        return new Scanner(System.in).nextInt();
    }

    public static String readStringInputWithMessage(String message){
        System.out.println(message);
        return readStringUserInput();
    }

    public static int readIntInputWithMessage(String message){
        System.out.println(message);
        return readIntUserInput();
    }

    private void initialParametersLoad() {
    }


}
