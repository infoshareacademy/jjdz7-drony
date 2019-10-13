package com.korpodrony.model;

import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "id = " + id + ", imię: " + name + ", nazwisko: " + surname;
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
