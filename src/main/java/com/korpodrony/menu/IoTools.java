package com.korpodrony.menu;

import java.util.Locale;
import java.util.Scanner;

public class IoTools {

    public static int getUserInput() {
        System.out.print("\nTwój wybór: ");
        return Integer.parseInt(readStringUserInput());
    }

    public static String readStringUserInput() {
        String text = sc.nextLine();
        return text.chars().allMatch(Character::isLetter) ? text : readStringInputWithMessage("Tylko litery są dozwolone. Spróbuj ponownie: ");
    }

    public static int readIntUserInput() {
        return new Scanner(System.in).nextInt();
    }

    public static String readStringInputWithMessage(String message) {
        System.out.println(message);
        return readStringUserInput();
    }

    public static int readIntInputWithMessage(String message) {
        System.out.println(message);
        return getNumericInput();
    }

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static double getUserInputDouble() {
        double x = 0;
        while (!sc.hasNextDouble()) {
            if (!sc.hasNext()) {
                System.err.println("no more input");
                System.exit(1);
            }
            System.out.println(sc.next() + ": is not a double, please enter a double ");
        }
        return sc.nextDouble();
    }

    public static short getUserInputShort() {
        short x = 0;
        while (!sc.hasNextShort()) {
            if (!sc.hasNext()) {
                System.err.println("no more input");
                System.exit(1);
            }
            System.out.println(sc.next() + ": nie jest liczbą z zakresu 1 do 32767. Spróbuj ponownie:");
        }
        x = sc.nextShort();
        return x;
    }

    public static byte getUserInputByte() {
        byte x = 0;
        while (!sc.hasNextByte()) {
            if (!sc.hasNext()) {
                System.err.println("no more input");
                System.exit(1);
            }
            System.out.println(sc.next() + ": nie jest liczbą z zakresu 1 do 127. Spróbuj ponownie:");
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

    public static int getNumericInput() {
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
        return getNumericInput();
    }

    public static int getIntegerWithMessage(String message) {
        System.out.println(message);
        return getNumericInput();
    }
}
