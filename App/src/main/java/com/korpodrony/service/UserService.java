package com.korpodrony.service;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.dao.OrganizationRepositoryDaoImpl;
import com.korpodrony.model.Activity;
import com.korpodrony.model.User;
import com.korpodrony.utils.IoTools;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    private OrganizationRepositoryDao organization = new OrganizationRepositoryDaoImpl();

    public void addUser() {
        String name = IoTools.getStringFromUserWithMessage("Podaj imię:");
        String surname = IoTools.getStringFromUserWithMessage("Podaj nazwisko:");
        if (organization.createUser(name, surname)) {
            System.out.println("Dodano użytkownika.");
        } else {
            System.out.println("Podany użytkownik już istnieje!");
        }
    }

    public void removeUser() {
        printUsers();
        if (organization.getAllUsers().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika do usunięcia:");
        if (organization.deleteUser(choice)) {
            System.out.println("Usunięto użytkownika.");
        } else {
            System.out.println("Użytkownik o takim ID nie istnieje!");
        }
    }

    public void showUserActivities() {
        printUsers();
        if (organization.getAllUsers().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika, do którego zajęcia chcesz obejrzeć:");
        if (!organization.hasUserWithThisID(choice)) {
            System.out.println("Nie ma takiego użytkownika.");
            return;
        }
        List<Activity> userActivities = organization.getAllActivities().stream()
                .filter(x -> x.getAssignedUsersIDs().contains(choice))
                .collect(Collectors.toList());
        if (userActivities.isEmpty()) {
            System.out.println("Użytkownik nie jest przypisany do żadnych zajęć.");
            return;
        }
        userActivities.sort(new ActivityIDComparator());
        userActivities.forEach(System.out::println);
    }

    public void printUsers() {
        printUsers(new UserIDComparator());
    }

    List<User> getUsersByIDs(Set<Integer> users) {
        List<User> resultUsers = new ArrayList<>();
        List<Integer> usersIDs = new ArrayList<>(users);
        Collections.sort(usersIDs);
        for (int i : usersIDs) {
            resultUsers.add(organization.getUser(i));
        }
        return resultUsers;
    }

    public void editUser() {
        if (organization.getAllUsers().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych użytkowników, których można by edytować.");
            return;
        }
        printUsers();
        int userID = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika, który chcesz edytować:");
        if (!organization.getAllUsersIDs().contains(userID)) {
            System.out.println("Nie ma użytkownika o takim ID!");
            return;
        }
        String name = IoTools.getStringFromUserWithMessage("Podaj nowe imię użytkownika:");
        String surName = IoTools.getStringFromUserWithMessage("Podaj nowe nazwisko użytkownika:");
        if (organization.editUser(userID, name, surName)) {
            System.out.println("Zedytowano użytkownika.");
        }
    }

    public void printUsers(Comparator comparator) {
        List<User> users = organization.getAllUsers();
        users.sort(comparator);
        if (users.isEmpty()) {
            System.out.println("Nie ma obecnie żadnych użytkowników.");
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }

    public void searchByUserMenu() {
        System.out.println("- Wpisz imię:");
        String searchedText = IoTools.getCharsOnlyStringFromUser().toLowerCase();
        List<User> users = organization.getAllUsers().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedText))
                .collect(Collectors.toList());
        if (users.isEmpty()) {
            System.out.println("Nie ma takiego użytkownika.");
            return;
        }
        users.sort(new UserIDComparator());
        users.forEach(System.out::println);
    }
}