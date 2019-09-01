package model;

import java.util.Objects;

public class User {

    private static int currentID = 0;
    private int userID;
    private String name;
    private String surname;
    private String birthDate;
    private String password;

    public User(int userID, String name, String surname, String birthDate, String password) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.password = password;
    }

    public User(String name, String surname, String birthDate, String password) {
        this(++currentID, name, surname, birthDate, password);
    }

    public void editUser(String name, String surname, String password, String birthday) {
        setName(name);
        setSurname(surname);
        setPassword(password);
        setBirthDate(birthday);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", password='" + password + '\'' +
                '}';
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
        return Objects.hash(userID, name, surname, birthDate);
    }
}
