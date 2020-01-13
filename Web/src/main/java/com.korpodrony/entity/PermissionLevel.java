package com.korpodrony.entity;

public enum PermissionLevel {

    ADMIN(0),
    USER(1),
    GUEST(2);

    int value;

    PermissionLevel(int number) {
        this.value = number;
    }

    public int getValue() {
        return value;
    }

    public static PermissionLevel getPermissionLevelFromInteger(int number) {
        return PermissionLevel.values()[number];
    }
}
