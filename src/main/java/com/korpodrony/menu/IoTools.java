package com.korpodrony.menu;

import java.util.Locale;
import java.util.Scanner;

public class IoTools {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static String getStringFromUser() {
        String text = sc.nextLine();
        return text.chars().allMatch(Character::isLetter) ? text : getStringFromUserWithMessage("Tylko litery są dozwolone. Spróbuj ponownie: ");
    } //TODO current method is over-sensitive - as some names might include numbers (eg activities names). To discuss

    public static String getStringFromUserWithMessage(String message) {
        System.out.println(message);
        return getStringFromUser();
    }

//    public static double getDoubleFromUser() {
//        double x = 0;
//        while (!sc.hasNextDouble()) {
//            if (!sc.hasNext()) {
//                System.err.println("no more input");
//                System.exit(1);
//            }
//            System.out.println(sc.next() + ": is not a double, please enter a double ");
//        }
//        return sc.nextDouble();
//    } TODO - is it necessary to keep this one?

    public static short getShortFromUser() {
        short x = 0;
        while (!sc.hasNextShort()) {
            if (!sc.hasNext()) {
                System.err.println("Brak danych!");
                System.exit(1);
            }
            System.out.println(sc.next() + ": nie jest liczbą z zakresu 1 do 32767. Spróbuj ponownie:");
        }
        x = sc.nextShort();
        return x;
    }

    public static short getShortFromUserWithMessage(String message) {
        System.out.println(message);
        short x = getShortFromUser();
        return x > 0 ? x : getShortFromUserWithMessage("Nie można przekazać wartości mniejszej od 1. Spróbuj ponownie:");
    }

    public static byte getByteFromUser() {
        byte x = 0;
        while (!sc.hasNextByte()) {
            if (!sc.hasNext()) {
                System.err.println("Brak danych!");
                System.exit(1);
            }
            System.out.println(sc.next() + ": nie jest liczbą z zakresu 1 do 127. Spróbuj ponownie:");
        }
        x = sc.nextByte();
        return x;
    }

    public static byte getByteFromUserWithMessage(String message) {
        System.out.println(message);
        byte x = getByteFromUser();
        return x > 0 ? x : getByteFromUserWithMessage("Nie można przekazać wartości mniejszej od 1. Spróbuj ponownie:");
    }


    public static int getIntFromUser() {
        int x;
        System.out.println("Twój wybór?");
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

    public static int getIntFromUserWithMessage(String message) {
        System.out.println(message);
        return getIntFromUser();
    }
}
