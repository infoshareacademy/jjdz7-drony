package com.korpodrony.model;

public enum ActivitiesType {

    LECTURES("wykład"),
    EXERCISES("ćwiczenia"),
    WORKSHOPS("warsztaty");

    private final String type;

    ActivitiesType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ActivitiesType{" +
                "type='" + type + '\'' +
                '}';
    }
}