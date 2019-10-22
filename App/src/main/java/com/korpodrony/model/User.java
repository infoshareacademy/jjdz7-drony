package com.korpodrony.model;

import java.util.Objects;
import java.util.Set;

public class User {

    private static int currentID = 0;
    private int id;
    private String name;
    private String surname;

    public User(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public User() {
    }
    public static void setCurrentID(Set<User> users){
        int maxValue = 0;
        for (User user: users){
            if (user.getId()>maxValue){
                maxValue=user.getId();
            }
        }
        setCurrentID(maxValue);
    }
    public User(String name, String surname) {
        this(++currentID, name, surname);
    }

    public void editUser(String name, String surname) {
        setName(name);
        setSurname(surname);
    }

    public static int getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(int currentID) {
        User.currentID = currentID;
    }

    public int getId() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Użytkownik " + "ID = " + id + ", imię: " + name + ", nazwisko: " + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
