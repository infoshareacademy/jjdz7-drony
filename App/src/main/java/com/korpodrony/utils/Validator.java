package com.korpodrony.utils;

public class Validator {
    private Validator() {
    }

    public static boolean containsOnlyLetters(String text) {
        return text.chars().allMatch(Character::isLetter);
    }

    public static boolean isPositive(short number) {
        return number > 0;
    }

    public static boolean isPositive(byte number){
        return number > 0;
    }

    public static boolean isPositive(int number){
        return number > 0;
    }
}
