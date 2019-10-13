package com.korpodrony.menu;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class IoTools {

    private static final Scanner sc = new Scanner(in).useLocale(Locale.US);
    private static final String WRONG_INPUT_MESSAGE = ": nie jest liczbą, lub jest liczbą z poza dozwolonego zakresu";

    public static int getIntegerWithMessage(String message) {
        out.println(message);
        return getNumericInput();
    }

    public static String readStringInputWithMessage(String message) {
        out.println(message);
        return readStringUserInput();
    }

    public static byte getByteWithMessage(String message) {
        out.println(message);
        return getUserInputByte();
    }

    public static short getShortWithMessage(String message) {
        out.println(message);
        return getUserInputShort();
    }

    private static int getNumericInput() {
        int result;
        while (!sc.hasNextInt()) {
            out.println(sc.next() + WRONG_INPUT_MESSAGE);
            sc.nextLine();
        }
        result = sc.nextInt();
        sc.nextLine();
        return result;
    }

    private static String readStringUserInput() {
        return sc.nextLine();
    }

    private static byte getUserInputByte() {
        while (!sc.hasNextByte()) {
            out.println(sc.next() + WRONG_INPUT_MESSAGE);
        }
        return sc.nextByte();
    }

    private static short getUserInputShort() {
        while (!sc.hasNextShort()) {
            out.println(sc.next() + WRONG_INPUT_MESSAGE);
        }
        return sc.nextShort();
    }
}