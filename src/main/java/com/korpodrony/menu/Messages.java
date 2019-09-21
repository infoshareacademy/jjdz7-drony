package com.korpodrony.menu;

public class Messages {
    static void printMainMenu() {
        System.out.println("\n1. Szukaj");
        System.out.println("2. Użytkownicy");
        System.out.println("3. Sale");
        System.out.println("4. Zajęcia");
        System.out.println("5. Plany");
        System.out.println("6. Wyjście");
    }

    static void printSearchMenu() {
        System.out.println("\n1. Szukaj po nazwie użytkownika");
        System.out.println("2. Szukaj po nazwie sali");
        System.out.println("3. Szukaj po nazwie zajęć");
        System.out.println("4. Szukaj po nazwie planu");
        System.out.println("5. Wyjście");
    }


    static void printUsersContextMenu() {
        System.out.println("Menu -- Użytkownicy --");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż listę");
        System.out.println("5. Przypisz użytkownika do zajęć");
        System.out.println("6. Usuń użytkownika z zajęć");
        System.out.println("7. Wyjście");
    }

    static void printActivitiesContextMenu() {
        System.out.println("Menu -- Zajęcia --");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż listę");
        System.out.println("5. Przypisz zajęcia do planu");
        System.out.println("6. Usuń zajęcia z planu");
        System.out.println("7. Wyjście");
    }

    static void printSchedulesContextMenu() {
        System.out.println("Menu -- Plany --");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż listę");
        System.out.println("5. Wyjście");
    }

    static void printBadInputErrorMessage() {
        System.out.println("Złe dane, dokonaj ponownego wyboru");
    }

    static void printFeatureNotImplementedYet() {
        System.out.println("Feature będzie wprowadzony w przyszłości");
    }
}
