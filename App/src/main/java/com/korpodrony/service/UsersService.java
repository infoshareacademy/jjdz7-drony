package com.korpodrony.service;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.dao.OrganizationRepositoryDaoImpl;
import com.korpodrony.model.Activity;
import com.korpodrony.model.User;
import com.korpodrony.utils.IoTools;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class UsersService {

    private OrganizationRepositoryDao dao = new OrganizationRepositoryDaoImpl();

    public void addUser() {
        String name = IoTools.getStringFromUserWithMessage("Podaj imię:");
        String surname = IoTools.getStringFromUserWithMessage("Podaj nazwisko:");
        if (dao.createUser(name, surname)) {
            out.println("Dodano użytkownika.");
        } else {
            out.println("Podany użytkownik już istnieje!");
        }
    }

    public void removeUser() {
        printUsers();
        if (dao.getAllUsers().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika do usunięcia:");
        if (dao.deleteUser(choice)) {
            out.println("Usunięto użytkownika.");
        } else {
            out.println("Użytkownik o takim ID nie istnieje!");
        }
    }

    public void editUser() {
        if (areThereUsersWithMessageOnFalse(dao.getAllUsers(), "Nie ma obecnie żadnych użytkowników, których można by edytować.")) {
            printUsers();
            int userID = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika, który chcesz edytować:");
            if (checkContitionWithMessageOnFalse(dao.getAllUsersIDs().contains(userID), "Nie ma użytkownika o takim ID!")) {
                String name = IoTools.getStringFromUserWithMessage("Podaj nowe imię użytkownika:");
                String surName = IoTools.getStringFromUserWithMessage("Podaj nowe nazwisko użytkownika:");
                if (dao.editUser(userID, name, surName)) {
                    out.println("Zedytowano użytkownika.");
                }
            }
        }
    }

    public void showUserActivities() {
        printUsers();
        if (!dao.getAllUsers().isEmpty()) {
            int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika, do którego zajęcia chcesz obejrzeć:");
            if (checkContitionWithMessageOnFalse(dao.hasUserWithThisID(choice), "Nie ma takiego użytkownika.")) {
                List<Activity> userActivities = dao.getAllActivities().stream()
                        .filter(x -> x.getAssignedUsersIDs().contains(choice))
                        .collect(Collectors.toList());
                if (userActivities.isEmpty()) {
                    out.println("Użytkownik nie jest przypisany do żadnych zajęć.");
                    return;
                }
                userActivities.sort(new ActivityIDComparator());
                userActivities.forEach(out::println);
            }
        }
    }

    public void printUsers() {
        printUsers(new UserIDComparator());
    }

    public void printUsers(Comparator comparator) {
        List<User> users = dao.getAllUsers();
        users.sort(comparator);
        if (areThereUsersWithMessageOnFalse(users, "Nie ma obecnie żadnych użytkowników.")) {
            users.forEach(out::println);
        }
    }

    public void searchUsersByName() {
        out.println("- Wpisz imię:");
        String searchedText = IoTools.getCharsOnlyStringFromUser()
                .toLowerCase();
        List<User> users = dao.getAllUsers().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedText))
                .collect(Collectors.toList());
        if (areThereUsersWithMessageOnFalse(users, "Nie ma takiego użytkownika.")) {
            users.sort(new UserIDComparator());
            users.forEach(out::println);
        }
    }

    public List<User> getUsersByIDs(Set<Integer> users) {
        return users.stream()
                .map(x -> dao.getUser(x))
                .collect(Collectors.toList());
    }

    private boolean checkContitionWithMessageOnFalse(boolean statement, String s) {
        if (!statement) {
            out.println(s);
            return false;
        }
        return true;
    }

    private boolean areThereUsersWithMessageOnFalse(List<User> allUsers, String s) {
        if (allUsers.isEmpty()) {
            out.println(s);
            return false;
        }
        return true;
    }
}