package com.korpodrony.model;

import java.util.Objects;

public class User {

    private static int currentID = 0;
    private int userID;
    private String name;
    private String surname;

    public User(int userID, String name, String surname) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
    }

    public User(String name, String surname) {
        this(++currentID, name, surname);
    }

    public void editUser(String name, String surname) {
        setName(name);
        setSurname(surname);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "userID = " + userID +", name = " + name + ", surname = " + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, name, surname);
    }
}
