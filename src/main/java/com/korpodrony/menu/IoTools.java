package com.korpodrony.menu;

import java.util.Scanner;

public class IoTools {
    /**
     * UTIL PART OF THE CLASS.
     * <p>
     * TODO - decide if this should be split to separate file
     */


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
}
