package com.korpodrony.menu;

import java.util.Locale;
import java.util.Scanner;

public class IoTools {

    public static int getUserInput() {
        System.out.print("\nTwój wybór: ");
        return Integer.parseInt(readStringUserInput());
    }

    public static String readStringUserInput() {
        return sc.nextLine();
    }

    public static String readStringInputWithMessage(String message) {
        System.out.println(message);
        return readStringUserInput();
    }

    public static int readIntInputWithMessage(String message) {
        System.out.println(message);
        return getIntFromUser();
    }

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static short getUserInputShort() {
        short x = 0;
        while (!sc.hasNextShort()) {
            if (!sc.hasNext()) {
                System.err.println("Brak danych!");
                System.exit(1);
            }
            System.out.println(sc.next() + ": nie jest liczbą z zakresu od 1 do 32767. Spróbuj ponownie:");
        }
        x = sc.nextShort();
        return x;
    }

    public static byte getUserInputByte() {
        byte x = 0;
        while (!sc.hasNextByte()) {
            if (!sc.hasNext()) {
                System.err.println("Brak danych!");
                System.exit(1);
            }
            System.out.println(sc.next() + ": nie jest liczbą z zakresu od 1 do 127. Spróbuj ponownie:");
        }
        x = sc.nextByte();
        return x;
    }

    public static byte getByteWithMessage(String message) {
        System.out.println(message);
        byte x = getUserInputByte();
        return x > 0 ? x : getByteWithMessage("Nie można przekazać wartości mniejszej od 1. Spróbuj ponownie:");
    }

    public static short getShortWithMessage(String message) {
        System.out.println(message);
        short x = getUserInputShort();
        return x > 0 ? x : getShortWithMessage("Nie można przekazać wartości mniejszej od 1. Spróbuj ponownie:");
    }

    public static int getIntFromUser() {
        System.out.println("Twój wybór:");
        int x;
        while (!sc.hasNextInt()) {
            System.out.println(sc.next() + ": nie jest liczbą. Spróbuj ponownie:");
            sc.nextLine();
        }
        x = sc.nextInt();
        sc.nextLine();
        if (x > 0) {
            return x;
        }
        System.out.println("Podana wartość musi być większa od zera. Spróbuj ponownie");
        return getIntFromUser();
    }

    public static int getIntegerWithMessage(String message) {
        System.out.println(message);
        return getIntFromUser();
    }
}
