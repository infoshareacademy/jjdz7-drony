package menu;

import java.util.Scanner;

public class menu {
    private boolean exit;

    public void start() {
        initialParametersLoad();
        do {
            printMainMenu();
            int choice =getUserInput();
            mainMenuDecide(choice);
        }while (!exit);
// TODO discuss whether our app should save changes on the fly, or should it ask at the end of the program to write app state/config etd
    }

    /** Menu choice logic **/

    private void mainMenuDecide(int choice) {
        switch (choice){
            case 1:{
//                searchMenu();
                break;
            }
            case 2:{
//                usersMenu();
                break;
            }
            case 3:{
//                roomsMenu();
                break;
            }
            case 4:{
//                activitiesMenu();
                break;
            }
            case 5:{
//                schedulesMenu();
                break;
            }
            case 6:{
                exit = true;
                break;
            }
            default:{
                printBadInputErrorMessage();
            }

        }
    }

    private void searchMenu(){}
    private void usersMenu(){}
    private void roomsMenu(){}
    private void activitiesMenu(){}
    private void scheduleMenu(){}

    /** Menu prints section **/

    private void printMainMenu(){}
//    1.	Szukaj
//    2.	Użytkownicy
//    3.	Sale
//    4.	Zajęcia
//    5.	Plany
//    6.	Wyjście


    private void printSearchMenu(){}

//1.	Szukaj po nazwie użytkownika
//2.	Szukaj po nazwie Sali
//3.	Szukaj po nazwie zajęć
//4.	Szukaj po nazwie planu
//5.	Wyjście

    private void printUserContextMenu(){}

    /** TODO move 1-4 to one function to avoid repeating same println commands*/


//    1.	Dodaj użytkowników / sale / zajęcia /plany w zależności od wyboru)
//    2.	Edytuj
//    3.	Usuń
//    4.	Pokaż
//    5.	Wyjście


    private void printRoomContextMenu(){}

//    1.	Dodaj użytkowników / sale / zajęcia /plany w zależności od wyboru)
//    2.	Edytuj
//    3.	Usuń
//    4.	Pokaż
//    5.	Wyjście

    private void printActivityContextMenu(){}

//    1.	Dodaj użytkowników / sale / zajęcia /plany w zależności od wyboru)
//    2.	Edytuj
//    3.	Usuń
//    4.	Pokaż
//    5.	Wyjście

    private void printScheduleContextMenu(){}

//    1.	Dodaj użytkowników / sale / zajęcia /plany w zależności od wyboru)
//    2.	Edytuj
//    3.	Usuń
//    4.	Pokaż
//    5.	Wyjście

    private void printUserSubMenu(){}

//    Podaj Imię
//    Podaj nazwisko
//    Podaj... TODO check all fields needed to get necessary input from user - applies to below 3 classes as well, since logic will be similar

    private void printRoomSubMenu(){}
    private void printActivitySubMenu(){}
    private void printScheduleSubMenu(){}

    private void printUserEndMenu(){}
    private void printRoomEndMenu(){}
    private void printActivityEndMenu(){}
    private void printScheduleEndMenu(){}

    private void printBadInputErrorMessage()
    /**
     * placeholder
     */

    /**
     * UTIL PART OF THE CLASS.
     *
     * TODO - decide if this should be split to separate file
     */


    private int getUserInput() {
        System.out.print("\nTwój wybór: ");
        return Integer.parseInt(readUserInput());
    }

    public static String readUserInput(){
        return new Scanner(System.in).nextLine();
    }

    public static String readInputWithMessage(String message){
        System.out.println(message);
        return readUserInput();
    }

    private void initialParametersLoad(){}


}
