package com.korpodrony.menu;

/** TODO below code is to be yet implement after Menu is is working conditions - function below

public class menuIO {

    private IO() {
    }

    public static String getUserInput(Scanner sc) {
        return sc.nextLine();
    }

    public static int getNumericInput(Scanner sc) {
        int x;
        while (!sc.hasNextInt()) {
            System.out.println(sc.next() + ": is not a number, please enter a number");
            sc.nextLine();
        }
        x = sc.nextInt();
        sc.nextLine();
        return x;
    }
}**/
