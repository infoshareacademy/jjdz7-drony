package com.korpodrony.service;

import com.korpodrony.menu.IoTools;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;

import java.util.*;

public class OrganizationService {
    private Organization organization;

    public OrganizationService(Organization organization) {
        this.organization = organization;
    }

    public void addUser() {
        String name = IoTools.readStringInputWithMessage("Podaj imię");
        String surname = IoTools.readStringInputWithMessage("Podaj nazwisko");
        if (organization.createUser(name, surname)) {
            System.out.println("Dodano użytownika");
        } else {
            System.out.println("Podany użytkownik już istnieje");
        }
    }

    public void addActivity() {
        String name = IoTools.readStringInputWithMessage("Podaj nazwę zajęć");
        short maxUsers = IoTools.getShortWithMessage("Podaj maksymalną liczbę użytkowników zajęć");
        byte duration = IoTools.getByteWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach");
        if (organization.createActivity(name, maxUsers, duration)) {
            System.out.println("Dodano zajęcia");
        } else {
            System.out.println("Takie zajęcia juz istnieją");
        }
    }

    public void addPlan() {
        String name = IoTools.readStringInputWithMessage("Podaj nazwę planu");
        if (organization.createPlan(name)) {
            System.out.println("Dodano plan");
        } else {
            System.out.println("Taki plan juz istnieje");
        }
    }

    public void assignUserToActivity() {
        if (organization.getAllActivies().size() > 0 && organization.getAllUsers().size() > 0) {
            int activityID = chooseActivity();
            if (organization.getActivity(activityID).getAssignedUsersIDs().size() != organization.getActivity(activityID).getMaxUsers()) {
                List<User> avaiableUsers = chooseAvaiableUsers(activityID);
                avaiableUsers.forEach(System.out::println);
                int userID = IoTools.readIntInputWithMessage("Podaj ID użytkownika, którego chcesz przypisać do zajęć");
                if (organization.assignUserToActivity(userID, activityID)) {
                    System.out.println("Przypisano użytkownika do zajęć");
                } else if (!organization.hasUserWithThisID(userID)) {
                    System.out.println("Użytkownik z takim ID nie istnieje");
                } else {
                    System.out.println("Ten użytkownik jest już przypisany do zajęć");
                }
            } else {
                System.out.println("Te zajęcia posiadają już maksylamną liczbę przypisanych uzytkowników");
            }
        } else {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników");
        }
    }


    public void assignActivityToPlan() {
        if (organization.getAllActivies().size() > 0 && organization.getAllPlans().size() > 0) {
            int planID = choosePlan();
            List<Activity> avaiableActivites = chooseAvaiableActivites(planID);
            avaiableActivites.forEach(System.out::println);
            int activityID = IoTools.readIntInputWithMessage("Podaj ID zajęć, które chcesz przypisać do planu");
            if (organization.assignActivityToPlan(activityID, planID)) {
                System.out.println("Przypisano użytkownika do zajęć");
            } else if (!organization.hasActivityWithThisID(activityID)) {
                System.out.println("Zajęcia z takim ID nie istnieją");
            } else {
                System.out.println("Te zajęcia są już przypisane do planu");
            }
        } else {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć");
        }
    }

    public void unassignActivityFromPlan() {
        if (organization.getAllPlans().size() > 0 && organization.getAllActivies().size() > 0) {
            int planID = choosePlan();
            if (organization.getPlan(planID).getActivitiesID().size() > 0) {
                getActivitiesByIDs(organization.getPlan(planID).getActivitiesID()).forEach(System.out::println);
                int activityID = IoTools.getIntegerWithMessage("Podaj ID zajęć do wypisania z planu");
                if (!organization.getAllActivitiesIDs().contains(activityID)) {
                    System.out.println("zajęcia o takim ID nie istnieją");
                } else if (organization.unassignActivityFromPlan(activityID, planID)) {
                    System.out.println("zajęcia wypisano z planu");
                } else {
                    System.out.println("zajęcia nie były przypisane do tego planu");
                }
            } else {
                System.out.println("Plan nie posiada przypisanych zajęć");
            }
        } else {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć");
        }
    }

    public void unassingUserFromActivity() {
        if (organization.getAllActivies().size() > 0 && organization.getAllUsers().size() > 0) {
            int activityID = chooseActivity();
            if (organization.getActivity(activityID).getAssignedUsersIDs().size() > 0) {
                getUsersByIDs(organization.getActivity(activityID).getAssignedUsersIDs()).forEach(System.out::println);
                int userID = IoTools.getIntegerWithMessage("Podaj ID użytkownika do wypisania z zajęć");
                if (!organization.getAllUsersIDs().contains(userID)) {
                    System.out.println("użytkownik o takim ID nie istnieje");
                } else if (organization.unassignUserFromActivity(userID, activityID)) {
                    System.out.println("użytkownika wypisano z zajęć");
                } else {
                    System.out.println("użytkownik nie był przypisany do tych zajeć");
                }
            } else {
                System.out.println("Zajęcia nie posiadają przypisanych użytkowników");
            }
        } else {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników");
        }
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
        int choice = IoTools.readIntInputWithMessage("Podaj numer zajęc");
        if (organization.hasActivityWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma zajęć o takim ID");
            return chooseActivity();
        }
    }

    private int choosePlan() {
        printPlans();
        int choice = IoTools.readIntInputWithMessage("Podaj numer planu");
        if (organization.hasPlanWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma planu o takim ID");
            return chooseActivity();
        }
    }

    public void removeUser() {
        printUsers();
        int choice = IoTools.readIntInputWithMessage("Podaj ID użytkownika do usunięcia");
        if (organization.deleteUser(choice)) {
            System.out.println("Usunięto użytkownika");
        } else {
            System.out.println("Użytkownik o takim ID nie istnieje");
        }
    }

    public void removeActivity() {
        printActivites();
        int choice = IoTools.readIntInputWithMessage("Podaj ID zajęc do usunięcia");
        if (organization.deleteActivity(choice)) {
            System.out.println("Usunięto zajęcia");
        } else {
            System.out.println("Wybrane zajęcia nie istnieją");
        }
    }

    public void removePlan() {
        printPlans();
        int choice = IoTools.readIntInputWithMessage("Podaj ID planu do usunięcia");
        if (organization.deleteActivity(choice)) {
            System.out.println("Usunięto plan");
        } else {
            System.out.println("Wybrany plan nie istnieje");
        }
    }

    public void printUsers() {
        List<User> users = organization.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }

    public void printActivites() {
        List<Activity> activities = organization.getAllActivies();
        for (int i = 0; i < activities.size(); i++) {
            System.out.println(activities.get(i));
        }
    }

    public void printPlans() {
        List<Plan> plans = organization.getAllPlans();
        for (int i = 0; i < plans.size(); i++) {
            System.out.println(plans.get(0));
        }
    }
}