package com.korpodrony.menu;

import java.util.Locale;
import java.util.Scanner;

public class IoTools {

    public static int getUserInput() {
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
            System.out.println(sc.next() + ": is not a short, please enter a short ");
        }
        return sc.nextShort();
    }

    public static byte getUserInputByte() {
        short x = 0;
        while (!sc.hasNextByte()) {
            if (!sc.hasNext()) {
                System.err.println("no more input");
                System.exit(1);
            }
            System.out.println(sc.next() + ": is not a byte, please enter a byte ");
        }
        return sc.nextByte();
    }

    public static byte getByteWithMessage(String message){
        System.out.println(message);
        return getUserInputByte();
    }

    public static short getShortWithMessage(String message){
        System.out.println(message);
        return getUserInputShort();
    }

    public static int getNumericInput() {
        int x;
        while (!sc.hasNextInt()) {
            System.out.println(sc.next() + ": is not a number, please enter a number");
            sc.nextLine();
        }
        x = sc.nextInt();
        sc.nextLine();
        return x;
    }

    public static int getIntegerWithMessage(String message){
        System.out.println(message);
        return getNumericInput();
    }

}
