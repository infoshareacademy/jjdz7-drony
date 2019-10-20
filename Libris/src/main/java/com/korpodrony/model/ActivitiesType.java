package com.korpodrony.model;

public enum ActivitiesType {

    LECTURE("wykład"),
    EXCERCISE("ćwiczenia"),
    WORKSHOP("warszaty");

    String polishName;

    ActivitiesType(String polishName) {
        this.polishName = polishName;
    }
}