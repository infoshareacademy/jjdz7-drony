package com.korpodrony.menu;

public class Messages {
    static void printMainMenu() {
        System.out.println("\n1. Szukaj");
        System.out.println("2. Użytkownicy");
//        System.out.println("3. Sale");
        System.out.println("3. Zajęcia");
        System.out.println("4. Plany");
        System.out.println("5. Wyjście");
    }

    static void printSearchMenu() {
        System.out.println("\n1. Szukaj po nazwie użytkownika");
        System.out.println("2. Szukaj po nazwie zajęć");
        System.out.println("3. Szukaj po nazwie planu");
        System.out.println("4. Wyjście");
    }

    static void printInitialMenu() {
        System.out.println("\n\n --" + "Libris by koprodrony" + "--");
        System.out.println("\n-- " + "MENU" + " --");
        System.out.println("\n1. Wczytaj z pliku");
        System.out.println("2. Stwórz nowy");
    }

    static void printContextMenu(String contextMenuType) {
        System.out.println("Menu --" + contextMenuType + "--");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż");
        System.out.println("5. Wyjście");
    }

    static void printUserMenu(String contextMenuType) {
        System.out.println("Menu --" + contextMenuType + "--");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż wszystkich użytkowników");
        System.out.println("5. Pokaż wszystkie zajęcia do których przypisany jest użytkownik");
        System.out.println("6. Wyjście");
    }

    static void printActivitiesMenu(String contextMenuType) {
        System.out.println("Menu --" + contextMenuType + "--");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż");
        System.out.println("5. Przypisz użytkownika do zajęć");
        System.out.println("6. Wypisz użytkownika z zajęć");
        System.out.println("7. Pokaż użytkowników przypisanych do zajęć");
        System.out.println("8. Wyjście");
    }

    static void printScheldulesMenu(String contextMenuType) {
        System.out.println("Menu --" + contextMenuType + "--");
        System.out.println("\n1. Dodaj");
        System.out.println("2. Edytuj");
        System.out.println("3. Usuń");
        System.out.println("4. Pokaż");
        System.out.println("5. Dołącz zajęcia do planu");
        System.out.println("6. Usuń zajęcia z planu");
        System.out.println("7. Pokaż zajęcia przypisanych do planu");
        System.out.println("8. Wyjście");
    }

    static void printBadInputErrorMessage() {
        System.out.println("Złe dane, dokonaj ponownego wyboru");
    }

    static void printFeatureNotImplementedYet() {
        System.out.println("Feature będzie wprowadzony w przyszłości");
    }
}
