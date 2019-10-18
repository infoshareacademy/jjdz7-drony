package com.korpodrony.model;

public enum ActivitiesType {

    LECTURE("WYKŁAD"),
    EXCERCISE("ĆWICZENIA"),
    WORKSHOP("WARSZTATY");

    String polishName;

    ActivitiesType(String polishName) {
        this.polishName = polishName;
    }
}