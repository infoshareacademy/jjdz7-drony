package menu;

import java.util.Scanner;

public class menu {
    private boolean exit;
    private boolean contextMenuExit;
    private boolean subMenuExit;

    public void start() {
        initialParametersLoad();
        do {
            printMainMenu();
            int choice = getUserInput();
            mainMenuDecide(choice);
        } while (!exit);
// TODO discuss whether our app should save changes on the fly, or should it ask at the end of the program to write app state/config etd
    }

    /**
     * Menu choice logic
     **/

    private void mainMenuDecide(int choice) {
        switch (choice) {
            case 1: {
//                searchMenu();
                break;
            }
            case 2: {
//                usersMenu();
                break;
            }
            case 3: {
//                roomsMenu();
                break;
            }
            case 4: {
//                activitiesMenu();
                break;
            }
            case 5: {

//                schedulesMenu();
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

    private void searchMenu() {

        do {
            printSearchMenu();
            int choice = getUserInput();
            searchMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void searchMenuDecide(int choice) {

    }

    private void usersMenu() {
        do {
            printContextMenu("Użytkownicy");
            int choice = getUserInput();
            usersMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void usersMenuDecide(int choice) {

    }

    private void roomsMenu() {
        printFeatureNotImplementedYet();
//        printContextMenu("Sale");
    }

    private void activitiesMenu() {
        do {
            printContextMenu("Zajęcia");
            int choice = getUserInput();
            activitiesMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void activitiesMenuDecide(int choice) {

    }

    private void schedulesMenu() {
        do {
            printContextMenu("Plany");
            int choice = getUserInput();
            schedulesMenuDecide(choice);
        } while (!contextMenuExit);
    }

    private void schedulesMenuDecide(int choice) {

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

    private void printUserSubMenu() {


    }

//    Podaj Imię
//    Podaj nazwisko
//    Podaj... TODO check all fields needed to get necessary input from user - applies to below 3 classes as well, since logic will be similar

    private void printRoomSubMenu() {
    }

    private void printActivitySubMenu() {
    }

    private void printScheduleSubMenu() {
    }


    private void printBadInputErrorMessage() {
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
        return Integer.parseInt(readUserInput());
    }

    public static String readUserInput() {
        return new Scanner(System.in).nextLine();
    }

    public static String readInputWithMessage(String message) {
        System.out.println(message);
        return readUserInput();
    }

    private void initialParametersLoad() {
    }


}
