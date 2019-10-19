package com.korpodrony.service;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.menu.IoTools;
import com.korpodrony.model.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrganizationService {
    private Organization organization;

    public OrganizationService(Organization organization) {
        this.organization = organization;
    }

    public void addUser() {
        String name = IoTools.readStringInputWithMessage("Podaj imię: ");
        String surname = IoTools.readStringInputWithMessage("Podaj nazwisko: ");
        if (organization.createUser(name, surname)) {
            System.out.println("Dodano użytownika.");
        } else {
            System.out.println("Podany użytkownik już istnieje!");
        }
    }

    public void addPlan() {
        String name = IoTools.readStringInputWithMessage("Podaj nazwę planu: ");
        if (organization.createPlan(name)) {
            System.out.println("Dodano plan.");
        } else {
            System.out.println("Podany plan już istnieje!");
        }
    }

    public void addActivity() {
        String name = IoTools.readStringInputWithMessage("Podaj nazwę zajęć: ");
        short maxUsers = IoTools.getShortWithMessage("Podaj maksymalną liczbę użytkowników zajęć: ");
        byte duration = IoTools.getByteWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach: ");
        ActivitiesType chosenActivity = choosingActivityType();
        if (organization.createActivity(name, maxUsers, duration, chosenActivity)) {
            System.out.println("Dodano zajęcia.");
        } else {
            System.out.println("Takie zajęcia już istnieją!");
        }
    }

    private ActivitiesType choosingActivityType () {

        ActivitiesType chosenActivityType;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj rodzaj zajęć: wykład, ćwiczenia, warsztaty");
            String activityInput = scanner.nextLine();
            chosenActivityType = decodeActivityTypeFromInputString(activityInput);

        } while (chosenActivityType == null);

        return chosenActivityType;
    }

    private ActivitiesType decodeActivityTypeFromInputString(String activityInput) {

        switch (activityInput) {
            case "wykład":

                return ActivitiesType.LECTURES;

            case "ćwiczenia":

                return  ActivitiesType.EXERCISES;

            case "warsztaty":

                return ActivitiesType.WORKSHOPS;

                default:
                    System.out.println("Podano błędne zajęcia. Podaj prawidłową nazwę: ");
                    return null;
        }
    }


    public void assignUserToActivity() {
        if (organization.getAllActivities().isEmpty() || organization.getAllUsers().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników!");
            return;
        }
        int activityID = chooseActivity();
        if (chooseAvailableUsers(activityID).isEmpty()) {
            System.out.println("Nie ma obecnie żadnych użytkowników, których można przypisać.");
            return;
        }
        List<User> avaiableUsers = chooseAvailableUsers(activityID);
        avaiableUsers.sort(new UserIDComparator());
        avaiableUsers.forEach(System.out::println);
        int userID = IoTools.readIntInputWithMessage("Podaj ID użytkownika, którego chcesz przypisać do zajęć: ");
        if (canAssignUsertToActivity(userID, activityID)) {
            if (organization.assignUserToActivity(userID, activityID)) {
                System.out.println("Przypisano użytkownika do zajęć.");
            }
        }
    }

    public void assignActivityToPlan() {
        if (organization.getAllActivities().isEmpty() || organization.getAllPlans().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć!");
            return;
        }
        int planID = choosePlan();
        if (chooseAvailableActivities(planID).isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć, które można przypisać!");
            return;
        }
        List<Activity> avaiableAcitvities = chooseAvailableActivities(planID);
        avaiableAcitvities.sort(new ActivityIDComparator());
        avaiableAcitvities.forEach(System.out::println);
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
            System.out.println("Te zajęcia są już przypisane do planu.");
            return false;
        }
        return true;
    }

    public void unassignActivityFromPlan() {
        if (organization.getAllPlans().isEmpty() || organization.getAllActivities().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć.");
            return;
        }
        int planID = choosePlan();
        if (organization.getPlan(planID).getActivitiesID().isEmpty()) {
            System.out.println("Nie ma obecnie przypisanych żadnych zajęć do planu.");
            return;
        }
        getActivitiesByIDs(organization.getPlan(planID).getActivitiesID()).forEach(System.out::println);
        int activityID = IoTools.getIntegerWithMessage("Podaj ID zajęć do wypisania z planu: ");
        if (canUnassignActivityFromPlan(activityID, planID)) {
            if (organization.unassignActivityFromPlan(activityID, planID)) {
                System.out.println("Zajęcia wypisano z planu.");
            }
        }
    }

    public void unassingUserFromActivity() {
        if (organization.getAllActivities().isEmpty() || organization.getAllUsers().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników!");
            return;
        }
        int activityID = chooseActivity();
        if (organization.getActivity(activityID).getAssignedUsersIDs().isEmpty()) {
            System.out.println("Zajęcia nie posiadają przypisanych użytkowników.");
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

    private List<User> chooseAvailableUsers(int activityID) {
        List<User> users = organization.getAllUsers();
        Set<Integer> activityUser = organization.getActivity(activityID).getAssignedUsersIDs();
        for (Integer i : activityUser) {
            for (int j = 0; j < users.size(); j++) {
                if (users.get(j).getID() == i) {
                    users.remove(j);
                }
            }
        }
        return users;
    }

    private List<Activity> chooseAvailableActivities(int planID) {
        List<Activity> activities = organization.getAllActivities();
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
        printActivities();
        int choice = IoTools.readIntInputWithMessage("Podaj ID zajęć: ");
        if (organization.hasActivityWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma zajęć o takim ID.");
            return chooseActivity();
        }
    }

    private int choosePlan() {
        printPlans();
        int choice = IoTools.readIntInputWithMessage("Podaj ID planu: ");
        if (organization.hasPlanWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma planu o takim ID.");
            return chooseActivity();
        }
    }

    public void removeUser() {
        printUsers();
        if (organization.getAllUsers().isEmpty()) {
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
        printActivities();
        if (organization.getAllActivities().isEmpty()) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID zajęć do usunięcia: ");
        if (organization.deleteActivity(choice)) {
            System.out.println("Usunięto zajęcia.");
        } else {
            System.out.println("Wybrane zajęcia nie istnieją!");
        }
    }

    public void removePlan() {
        printPlans();
        if (organization.getAllPlans().isEmpty()) {
            return;
        }
        int choice = IoTools.readIntInputWithMessage("Podaj ID planu do usunięcia: ");
        if (organization.deletePlan(choice)) {
            System.out.println("Usunięto plan.");
        } else {
            System.out.println("Wybrany plan nie istnieje.");
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

    public void printActivities(Comparator comparator) {

        List<Activity> activities = organization.getAllActivities();
        activities.sort(comparator);
        if (activities.isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć.");
            return;
        }
        System.out.println("\nPodaj numer, by wybrać rodzaj zajęć (1 - wykład, 2 - ćwiczenia, 3- warsztaty, 4 - wszystkie): ");
        int typeChoice = IoTools.getIntFromUser();
        activityTypeDecide(typeChoice, activities);
    }

    private void activityTypeDecide(int typeChoice, List<Activity> activities) {
        switch (typeChoice) {
            case 1: {
                Predicate<Activity> filter = a -> a.getActivitiesType().equals(ActivitiesType.LECTURES);
                printFilteredActivities(filter, activities);
                break;
            }
            case 2: {
                Predicate<Activity> filter = a -> a.getActivitiesType().equals(ActivitiesType.EXERCISES);
                printFilteredActivities(filter, activities);
                break;
            }
            case 3: {
                Predicate<Activity> filter = a -> a.getActivitiesType().equals(ActivitiesType.WORKSHOPS);
                printFilteredActivities(filter, activities);
                break;
            }
            default: {
                Predicate<Activity> filter = a -> true;
                printFilteredActivities(filter, activities);
            }
        }
    }

    private void printFilteredActivities(Predicate<? super Activity> filter, List<Activity> activities) {

        List<Activity> activityList = activities.stream()
                .filter(filter)
                .collect(Collectors.toList());
        if (activityList.isEmpty()) {
            System.out.println("Nie ma zajęć spełniających podane kryteria.");
        } else {
            activityList.forEach(System.out::println);
        }
    }

    public void printPlans(Comparator comparator) {
        List<Plan> plans = organization.getAllPlans();
        plans.sort(comparator);
        if (plans.isEmpty()) {
            System.out.println("Nie ma obecnie żadnych planów.");
            return;
        }
        for (int i = 0; i < plans.size(); i++) {
            System.out.println(plans.get(0));
        }
    }

    public void printPlans() {
        printPlans(new PlanIDComparator());
    }

    public void printActivities() {
        printActivities(new ActivityIDComparator());
    }

    public void printUsers() {
        printUsers(new UserIDComparator());
    }

    public void editPlan() {
        if (organization.getAllPlans().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych planów, które można by edytować.");
            return;
        }
        printPlans();
        int planID = IoTools.getIntegerWithMessage("Podaj ID planu, który chcesz edytować: ");
        if (!organization.getAllPlansIDs().contains(planID)) {
            System.out.println("Nie ma planu o takim ID.");
            return;
        }
        String name = IoTools.readStringInputWithMessage("Podaj nową nazwę zajęć: ");
        if (organization.editPlan(planID, name)) {
            System.out.println("Zmieniono plan.");
        }
    }

    public void editActivity() {
        if (organization.getAllActivities().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć, które można by edytować.");
            return;
        }
        printActivities();
        int activityID = IoTools.getIntegerWithMessage("Podaj ID zajęć, które chcesz edytować:");
        if (!organization.getAllActivitiesIDs().contains(activityID)) {
            System.out.println("Nie ma zajęć o takim ID.");
            return;
        }
        String name = IoTools.readStringInputWithMessage("Podaj nazwę zajęć: ");
        short maxUsers = IoTools.getShortWithMessage("Podaj maksymalną liczbę użytkowników zajęć: ");
        byte duration = IoTools.getByteWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach: ");
        ActivitiesType chosenActivity = choosingActivityType();
        if (organization.editActivity(activityID, name, maxUsers, duration, chosenActivity)) {
            System.out.println("Dokonano edycji zajęć");
        }
    }

    public void editUser() {
        if (organization.getAllUsers().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych użytkowników, których można by edytować.");
            return;
        }
        printUsers();
        int userID = IoTools.getIntegerWithMessage("Podaj ID użytkownika, który chcesz edytować: ");
        if (!organization.getAllUsersIDs().contains(userID)) {
            System.out.println("Nie ma użytkownika o takim ID.");
            return;
        }
        String name = IoTools.readStringInputWithMessage("Podaj nowe imię użytkownika: ");
        String surName = IoTools.readStringInputWithMessage("Podaj nowe nazwisko użytkownika: ");
        if (organization.editUser(userID, name, surName)) {
            System.out.println("Wyedytowano użytkownika.");
        }
    }
}