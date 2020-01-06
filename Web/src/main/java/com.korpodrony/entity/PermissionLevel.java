package com.korpodrony.entity;

public enum PermissionLevel {

    ADMIN(0),
    USER(1),
    GUEST(2);

    int number;

    public int getNumber() {
        return number;
    }

    PermissionLevel(int number) {
        this.number = number;
    }
}
