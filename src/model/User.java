package model;

public class User {

    private static int currentID = 0;
    private int userID;
    private String name;
    private String surname;
    private String birthDate;
    private String password;

    public User(String name, String surname, String birthDate, String password) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.password = password;
        this.userID = ++currentID;
    }

    public short getAge(){
        return 1;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
