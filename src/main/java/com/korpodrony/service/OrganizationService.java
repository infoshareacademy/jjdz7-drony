package com.korpodrony.service;

import com.korpodrony.menu.IoTools;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OrganizationService {
    private Organization organization;

    public OrganizationService(Organization organization) {
        this.organization = organization;
    }

    public void addUser() {
        String name = IoTools.readStringInputWithMessage("Podaj imię:");
        String surname = IoTools.readStringInputWithMessage("Podaj nazwisko:");
        if (organization.createUser(name, surname)) {
            System.out.println("Dodano użytownika.");
        } else {
            System.out.println("Podany użytkownik już istnieje!");
        }
    }

    public void addActivity() {
        String name = IoTools.readStringInputWithMessage("Podaj nazwę zajęć:");
        short maxUsers = IoTools.getShortWithMessage("Podaj maksymalną liczbę użytkowników zajęć:");
        byte duration = IoTools.getByteWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach:");
        if (organization.createActivity(name, maxUsers, duration)) {
            System.out.println("Dodano zajęcia.");
        } else {
            System.out.println("Takie zajęcia już istnieją!");
        }
    }

    public void addPlan() {
        String name = IoTools.readStringInputWithMessage("Podaj nazwę planu:");
        if (organization.createPlan(name)) {
            System.out.println("Dodano plan.");
        } else {
            System.out.println("Taki plan juz istnieje!");
        }
    }

    public void assignUserToActivity() {
        if (organization.getAllActivies().size() == 0 || organization.getAllUsers().size() == 0) {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników!");
            return;
        }
        int activityID = chooseActivity();
        if (chooseAvaiableUsers(activityID).size() == 0) {
            System.out.println("Obecnie nie ma użytkowników, których można przypisać!");
            return;
        }
        chooseAvaiableUsers(activityID).forEach(System.out::println);
        int userID = IoTools.readIntInputWithMessage("Podaj ID użytkownika, którego chcesz przypisać do zajęć:");
        if (canAssignUsertToActivity(userID, activityID)) {
            if (organization.assignUserToActivity(userID, activityID)) {
                System.out.println("Przypisano użytkownika do zajęć.");
            }
        }
    }

    public void assignActivityToPlan() {
        if (organization.getAllActivies().size() == 0 || organization.getAllPlans().size() == 0) {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć!");
            return;
        }
        int planID = choosePlan();
        if (chooseAvaiableActivites(planID).size() == 0) {
            System.out.println("Obecnie nie ma zajęć, które można przypisać");
            return;
        }
        chooseAvaiableActivites(planID).forEach(System.out::println);
        int activityID = IoTools.readIntInputWithMessage("Podaj ID zajęć, które chcesz przypisać do planu:");
        if (canAssignActivityToPlan(activityID, planID)) {
            if (organization.assignActivityToPlan(activityID, planID)) {
                System.out.println("Zajęcia przypisano do planu.");
            }
        }
    }

    public boolean canAssignUsertToActivity(int userID, int activityID) {
        if (!organization.hasUserWithThisID(userID)) {
            System.out.println("Użytkownik z takim ID nie istnieje!");
            return false;
        } else if (organization.getActivity(activityID).getAssignedUsersIDs().contains(userID)) {
            System.out.println("Ten użytkownik jest już przypisany do zajęć!");
            return false;
        } else if (organization.getActivity(activityID).getAssignedUsersIDs().size() == organization.getActivity(activityID).getMaxUsers()) {
            System.out.println("Te zajęcia posiadają już maksymalną liczbę przypisanych użytkowników!");
            return false;
        }
        return true;
    }

    public boolean canAssignActivityToPlan(int activityID, int planID) {
        if (!organization.hasActivityWithThisID(activityID)) {
            System.out.println("Zajęcia z takim ID nie istnieją!");
            return false;
        } else if (organization.getPlan(planID).getActivitiesID().contains(activityID)) {
            System.out.println("Te zajęcia są już przypisane do planu!");
            return false;
        }
        return true;
    }

    public void unassignActivityFromPlan() {
        if (organization.getAllPlans().size() == 0 || organization.getAllActivies().size() == 0) {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć!");
            return;
        }
        int planID = choosePlan();
        if (organization.getPlan(planID).getActivitiesID().size() == 0) {
            System.out.println("Obecnie nie ma przypisanych żadnych zajęć do planu!");
            return;
        }
        getActivitiesByIDs(organization.getPlan(planID).getActivitiesID()).forEach(System.out::println);
        int activityID = IoTools.getIntegerWithMessage("Podaj ID zajęć do wypisania z planu:");
        if (canUnassignActivityFromPlan(activityID, planID)) {
            if (organization.unassignActivityFromPlan(activityID, planID)) {
                System.out.println("Zajęcia wypisano z planu.");
            }
        }
    }

    public void unassingUserFromActivity() {
        if (organization.getAllActivies().size() == 0 || organization.getAllUsers().size() == 0) {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników!");
            return;
        }
        int activityID = chooseActivity();
        if (organization.getActivity(activityID).getAssignedUsersIDs().size() == 0) {
            System.out.println("Zajęcia nie posiadają przypisanych użytkowników!");
            return;
        }
        getUsersByIDs(organization.getActivity(activityID).getAssignedUsersIDs()).forEach(System.out::println);
        int userID = IoTools.getIntegerWithMessage("Podaj ID użytkownika do wypisania z zajęć:");
        if (canUnassignUserFromActivity(userID, activityID)) {
            if (organization.unassignUserFromActivity(userID, activityID)) {
                System.out.println("Użytkownika wypisano z zajęć.");
            }
        }
    }

    public boolean canUnassignActivityFromPlan(int activityID, int planID) {
        if (!organization.getAllActivitiesIDs().contains(activityID)) {
            System.out.println("Zajęcia o takim ID nie istnieją!");
            return false;
        } else if (!organization.getPlan(planID).getActivitiesID().contains(activityID)) {
            System.out.println("Zajęcia nie są przypisane do planu, więc nie można ich wypisać.");
            return false;
        }
        return true;
    }

    public boolean canUnassignUserFromActivity(int userID, int activityID) {
        if (!organization.getAllUsersIDs().contains(userID)) {
            System.out.println("Użytkownik o takim ID nie istnieje!");
            return false;
        } else if (!organization.getActivity(activityID).getAssignedUsersIDs().contains(userID)) {
            System.out.println("Użytkownik nie jest przypisany do tych zajęć, więc nie może być usunięty.");
            return false;
        }
        return true;
    }

    private List<User> getUsersByIDs(Set<Integer> users) {
        List<User> resultUsers = new ArrayList<>();
        List<Integer> usersIDs = new ArrayList<>(users);
        Collections.sort(usersIDs);
        for (int i : usersIDs) {
            resultUsers.add(organization.getUser(i));
        }
        return resultUsers;
    }

    private List<Activity> getActivitiesByIDs(Set<Integer> activities) {
        List<Activity> resultActivity = new ArrayList<>();
        List<Integer> activitiesIDs = new ArrayList<>(activities);
        Collections.sort(activitiesIDs);
        for (int i : activitiesIDs) {
            resultActivity.add(organization.getActivity(i));
        }
        return resultActivity;
    }

    private List<User> chooseAvaiableUsers(int actityID) {
        List<User> users = organization.getAllUsers();
        Set<Integer> activityUser = organization.getActivity(actityID).getAssignedUsersIDs();
        for (Integer i : activityUser) {
            for (int j = 0; j < users.size(); j++) {
                if (users.get(j).getID() == i) {
                    users.remove(j);
                }
            }
        }
        return users;
    }

    private List<Activity> chooseAvaiableActivites(int planID) {
        List<Activity> activities = organization.getAllActivies();
        Set<Integer> activitesFromPlan = organization.getPlan(planID).getActivitiesID();
        for (Integer i : activitesFromPlan) {
            for (int j = 0; j < activitesFromPlan.size(); j++) {
                if (activities.get(j).getID() == i) {
                    activities.remove(j);
                }
            }
        }
        return activities;
    }

    private int chooseActivity() {
        printActivites();
        int choice = IoTools.readIntInputWithMessage("Podaj ID zajęć:");
        if (organization.hasActivityWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma zajęć o takim ID!");
            return chooseActivity();
        }
    }

    private int choosePlan() {
        printPlans();
        int choice = IoTools.readIntInputWithMessage("Podaj ID planu:");
        if (organization.hasPlanWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma planu o takim ID!");
            return chooseActivity();
        }
    }

    public void removeUser() {
        printUsers();
        if (organization.getAllUsers().size() == 0) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID użytkownika do usunięcia:");
        if (organization.deleteUser(choice)) {
            System.out.println("Usunięto użytkownika.");
        } else {
            System.out.println("Użytkownik o takim ID nie istnieje!");
        }
    }

    public void removeActivity() {
        printActivites();
        if (organization.getAllActivies().size() == 0) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID zajęć do usunięcia:");
        if (organization.deleteActivity(choice)) {
            System.out.println("Usunięto zajęcia.");
        } else {
            System.out.println("Wybrane zajęcia nie istnieją!");
        }
    }

    public void removePlan() {
        printPlans();
        if (organization.getAllPlans().size() == 0) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID planu do usunięcia:");
        if (organization.deletePlan(choice)) {
            System.out.println("Usunięto plan.");
        } else {
            System.out.println("Wybrany plan nie istnieje!");
        }
    }

    public void printUsers() {
        List<User> users = organization.getAllUsers();
        if (users.size() == 0) {
            System.out.println("Nie ma obecnie żadnych użytkowników!");
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }

    public void printActivites() {
        List<Activity> activities = organization.getAllActivies();
        if (activities.size() == 0) {
            System.out.println("Nie ma obecnie żadnych zajęć.");
            return;
        }
        for (int i = 0; i < activities.size(); i++) {
            System.out.println(activities.get(i));
        }
    }

    public void printPlans() {
        List<Plan> plans = organization.getAllPlans();
        if (plans.size() == 0) {
            System.out.println("Nie ma obecnie żadnych planów.");
            return;
        }
        for (int i = 0; i < plans.size(); i++) {
            System.out.println(plans.get(0));
        }
    }

    public void editPlan() {
        if (organization.getAllPlans().size() == 0) {
            System.out.println("Nie ma obecnie żadnych planów, które można by edytować.");
            return;
        }
        printPlans();
        int planID = IoTools.getIntegerWithMessage("Podaj ID planu, który chcesz edytować:");
        if (!organization.getAllPlansIDs().contains(planID)) {
            System.out.println("Nie ma planu o takim ID.");
            return;
        }
        String name = IoTools.readStringInputWithMessage("Podaj nową nazwę zajęć:");
        if (organization.editPlan(planID, name)) {
            System.out.println("Zedytowano plan.");
        }
    }

    public void editActivity() {
        if (organization.getAllActivies().size() == 0) {
            System.out.println("Nie ma obecnie żadnych zajęć, które można by edytować.");
            return;
        }
        printActivites();
        int activityID = IoTools.getIntegerWithMessage("Podaj ID zajęć, które chcesz edytować:");
        if (!organization.getAllActivitiesIDs().contains(activityID)) {
            System.out.println("Nie ma zajęć o takim ID!");
            return;
        }
        String name = IoTools.readStringInputWithMessage("Podaj nazwę zajęć:");
        short maxUsers = IoTools.getShortWithMessage("Podaj maksymalną liczbę użytkowników zajęć:");
        byte duration = IoTools.getByteWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach:");
        if (organization.editActivity(activityID, name, maxUsers, duration)) {
            System.out.println("Zedytowano zajęcia.");
        }
    }

    public void editUser() {
        if (organization.getAllUsers().size() == 0) {
            System.out.println("Nie ma obecnie żadnych użytkowników, których można by edytować.");
            return;
        }
        printUsers();
        int userID = IoTools.getIntegerWithMessage("Podaj ID użytkownika, który chcesz edytować:");
        if (!organization.getAllUsersIDs().contains(userID)) {
            System.out.println("Nie ma użytkownika o takim ID!");
            return;
        }
        String name = IoTools.readStringInputWithMessage("Podaj nowe imię użytkownika:");
        String surName = IoTools.readStringInputWithMessage("Podaj nowe nazwisko użytkownika:");
        if (organization.editUser(userID, name, surName)) {
            System.out.println("Zedytowano użytkownika.");
        }
    }
}