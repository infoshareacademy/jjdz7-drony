package com.korpodrony.model;

public enum ActivitiesType {

    LECTURE("wykład"),
    EXCERCISE("ćwiczenia"),
    WORKSHOP("warszaty");

    String polishName;

    ActivitiesType(String polishName) {
        this.polishName = polishName;
    }

    public static ActivitiesType getActivity(int number) {
        return ActivitiesType.values()[number];
    }
}