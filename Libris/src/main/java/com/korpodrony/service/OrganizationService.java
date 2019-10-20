package com.korpodrony.service;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.utils.IoTools;
import com.korpodrony.model.*;
import com.korpodrony.utils.JSONReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO split it to smaller services and make them using the same static repository!!!!!!!!!!
public class OrganizationService {
    private Organization organization;

    public OrganizationService(Organization organization) {
        this.organization = organization;
    }

    //TODO ------------------------------- methods moved from menu start here ---------------------------------------------------

    public static Organization loadParametersFromFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        Organization org = new Organization();

        if (path == null) {
            return org;
        } else {

            if (Files.exists(Paths.get(path, "Users.json"))) {
                Set<User> usersFromJson = new JSONReader().parseUserFromJSONFile(Paths.get(path, "Users.json"));
                org.setUsers(usersFromJson);
                User.setCurrentID(usersFromJson);
            }
            if (Files.exists(Paths.get(path, "Activities.json"))) {
                Set<Activity> activtitiesFromJson = new JSONReader().parseActivityFromJSONFile(Paths.get(path, "Activities.json"));
                org.setActivities(activtitiesFromJson);
                Activity.setCurrentID(activtitiesFromJson);
            }
            if (Files.exists(Paths.get(path, "Plans.json"))) {
                Set<Plan> plansFromJson = new JSONReader().parsePlanFromJSONFile(Paths.get(path, "Plans.json"));
                org.setPlans(plansFromJson);
                Plan.setCurrentID(plansFromJson);
            }

            return org;
        }
    }

    public void showAssignedUsers() {
        printActivities();
        if (organization.getAllActivities().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID zajęć, których użytkowników chcesz obejrzeć:");
        if (!organization.hasActivityWithThisID(choice)) {
            System.out.println("Nie ma takich zajęć.");
            return;
        }
        List<User> users = organization.getActivity(choice).getAssignedUsersIDs().stream().map(x -> organization.getUser(x)).collect(Collectors.toList());
        if (users.isEmpty()) {
            System.out.println("Zajęcia nie mają przypisanych żadnych użytkowników!");
            return;
        }
        users.sort(new UserIDComparator());
        users.forEach(System.out::println);

    }

    public void showActivitiesOfSchedule() {
        printPlans();
        if (organization.getAllPlans().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu, którego zajęcia chcesz obejrzeć: ");
        if (!organization.hasPlanWithThisID(choice)) {
            System.out.println("Nie ma takiego planu.");
            return;
        }
        List<Activity> activities = organization.getPlan(choice).getActivitiesID().stream().map(x -> organization.getActivity(x)).collect(Collectors.toList());
        if (activities.isEmpty()) {
            System.out.println("Plan nie ma przypisanych żadnych zajęć!");
            return;
        }
        activities.sort(new UserIDComparator());
        activities.forEach(System.out::println);
    }


    public void searchByUserMenu() {
        System.out.println("- Wpisz imię:");
        String searchedText = IoTools.getStringFromUser().toLowerCase();
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

    public void searchByActivityMenu() {
        System.out.println("- Wpisz nazwę:");
        String searchedText = IoTools.getStringFromUser().toLowerCase();
        List<Activity> activities = organization.getAllActivities().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedText))
                .collect(Collectors.toList());

        if (activities.isEmpty()) {
            System.out.println("Nie ma takich zajęć.");
            return;
        }
        activities.sort(new ActivityIDComparator());
        activities.forEach(System.out::println);
    }

    public void searchByScheduleMenu() {
        System.out.println("- Wpisz nazwę:");
        String searchedText = IoTools.getStringFromUser().toLowerCase();
        List<Plan> plans = organization.getAllPlans().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedText))
                .collect(Collectors.toList());

        if (plans.isEmpty()) {
            System.out.println("Nie ma takiego planu.");
            return;
        }
        plans.sort(new PlanIDComparator());
        plans.forEach(System.out::println);
    }

    public void showUserActivities() {
        printUsers();
        if (organization.getAllUsers().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika, do którego zajęcia chcesz obejrzeć:");
        if (!organization.hasUserWithThisID(choice)){
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


    //TODO ------------------------------- methods moved from menu end here ---------------------------------------------------

    public void addUser() {
        String name = IoTools.getStringFromUserWithMessage("Podaj imię:");
        String surname = IoTools.getStringFromUserWithMessage("Podaj nazwisko:");
        if (organization.createUser(name, surname)) {
            System.out.println("Dodano użytkownika.");
        } else {
            System.out.println("Podany użytkownik już istnieje!");
        }
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

    public void assignUserToActivity() {
        if (organization.getAllActivities().isEmpty() || organization.getAllUsers().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników.");
            return;
        }
        int activityID = chooseActivity();
        if (chooseAvailableUsers(activityID).isEmpty()) {
            System.out.println("Nie ma obecnie żadnych użytkowników, których można przypisać.");
            return;
        }
        List<User> availableUsers = chooseAvailableUsers(activityID);
        availableUsers.sort(new UserIDComparator());
        availableUsers.forEach(System.out::println);
        int userID = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika, którego chcesz przypisać do zajęć:");
        if (canAssignUserToActivity(userID, activityID)) {
            if (organization.assignUserToActivity(userID, activityID)) {
                System.out.println("Przypisano użytkownika do zajęć.");
            }
        }
    }


    public void unassignUserFromActivity() {
        if (organization.getAllActivities().isEmpty() || organization.getAllUsers().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć lub użytkowników.");
            return;
        }
        int activityID = chooseActivity();
        if (organization.getActivity(activityID).getAssignedUsersIDs().isEmpty()) {
            System.out.println("Zajęcia nie posiadają przypisanych użytkowników.");
            return;
        }
        getUsersByIDs(organization.getActivity(activityID).getAssignedUsersIDs()).forEach(System.out::println);
        int userID = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika do wypisania z zajęć:");
        if (canUnassignUserFromActivity(userID, activityID)) {
            if (organization.unassignUserFromActivity(userID, activityID)) {
                System.out.println("Użytkownika wypisano z zajęć.");
            }
        }
    }

    public boolean canAssignUserToActivity(int userID, int activityID) {
        if (!organization.hasUserWithThisID(userID)) {
            System.out.println("Użytkownik z takim ID nie istnieje!");
            return false;
        } else if (organization.getActivity(activityID).getAssignedUsersIDs().contains(userID)) {
            System.out.println("Ten użytkownik jest już przypisany do zajęć.");
            return false;
        } else if (organization.getActivity(activityID).getAssignedUsersIDs().size() == organization.getActivity(activityID).getMaxUsers()) {
            System.out.println("Te zajęcia posiadają już maksymalną liczbę przypisanych użytkowników!");
            return false;
        }
        return true;
    }

    public void addActivity() {
        String name = IoTools.getStringFromUserWithMessage("Podaj nazwę zajęć:");
        short maxUsers = IoTools.getShortFromUserWithMessage("Podaj maksymalną liczbę użytkowników zajęć:");
        byte lenghtInQuarters = IoTools.getByteFromUserWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach:");
        ActivitiesType chosenActivity = choosingActivityType();
        if (organization.createActivity(name, maxUsers, lenghtInQuarters, chosenActivity)) {
            System.out.println("Dodano zajęcia");
        } else {
            System.out.println("Takie zajęcia już istnieją");
        }
    }

    public void addPlan() {
        String name = IoTools.getStringFromUserWithMessage("Podaj nazwę planu:");
        if (organization.createPlan(name)) {
            System.out.println("Dodano plan.");
        } else {
            System.out.println("Taki plan juz istnieje!");
        }
    }

    public void assignActivityToPlan() {
        if (organization.getAllActivities().isEmpty() || organization.getAllPlans().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć.");
            return;
        }
        int planID = choosePlan();
        if (chooseAvailableActivities(planID).isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć, które można przypisać.");
            return;
        }
        List<Activity> availableActivities = chooseAvailableActivities(planID);
        availableActivities.sort(new ActivityIDComparator());
        availableActivities.forEach(System.out::println);
        int activityID = IoTools.getIntFromUserWithMessage("Podaj ID zajęć, które chcesz przypisać do planu:");
        if (canAssignActivityToPlan(activityID, planID)) {
            if (organization.assignActivityToPlan(activityID, planID)) {
                System.out.println("Zajęcia przypisano do planu.");
            }
        }
    }

    public boolean canAssignActivityToPlan(int activityID, int planID) {
        if (!organization.hasActivityWithThisID(activityID)) {
            System.out.println("Zajęcia z takim ID nie istnieją!");
            return false;
        } else if (organization.getPlan(planID).getActivitiesID().contains(activityID)) {
            System.out.println("Te zajęcia są już przypisane do tego planu!");
            return false;
        }
        return true;
    }

    public void unassignActivityFromPlan() {
        if (organization.getAllPlans().size() == 0 || organization.getAllActivities().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych planów lub zajęć.");
            return;
        }
        int planID = choosePlan();
        if (organization.getPlan(planID).getActivitiesID().isEmpty()) {
            System.out.println("Nie ma obecnie przypisanych żadnych zajęć do planu.");
            return;
        }
        getActivitiesByIDs(organization.getPlan(planID).getActivitiesID()).forEach(System.out::println);
        int activityID = IoTools.getIntFromUserWithMessage("Podaj ID zajęć do wypisania z planu:");
        if (canUnassignActivityFromPlan(activityID, planID)) {
            if (organization.unassignActivityFromPlan(activityID, planID)) {
                System.out.println("Zajęcia wypisano z planu.");
            }
        }
    }

    public boolean canUnassignActivityFromPlan(int activityID, int planID) {
        if (!organization.getAllActivitiesIDs().contains(activityID)) {
            System.out.println("Zajęcia o takim ID nie istnieją.");
            return false;
        } else if (!organization.getPlan(planID).getActivitiesID().contains(activityID)) {
            System.out.println("Zajęcia nie są przypisane do planu, więc nie można ich wypisać.");
            return false;
        }
        return true;
    }

    public boolean canUnassignUserFromActivity(int userID, int activityID) {
        if (!organization.getAllUsersIDs().contains(userID)) {
            System.out.println("Użytkownik o takim ID nie istnieje.");
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

    private List<User> chooseAvailableUsers(int actityID) {
        List<User> users = organization.getAllUsers();
        Set<Integer> activityUser = organization.getActivity(actityID).getAssignedUsersIDs();
        for (Integer i : activityUser) {
            for (int j = 0; j < users.size(); j++) {
                if (users.get(j).getId() == i) {
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
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID zajęć:");
        if (organization.hasActivityWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma zajęć o takim ID.");
            return chooseActivity();
        }
    }

    private int choosePlan() {
        printPlans();
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu:");
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
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID użytkownika do usunięcia:");
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
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID zajęć do usunięcia:");
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
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu do usunięcia:");
        if (organization.deletePlan(choice)) {
            System.out.println("Usunięto plan.");
        } else {
            System.out.println("Wybrany plan nie istnieje!");
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
            System.out.println("Nie ma obecnie żadnych zajęć");
            return;
        }
        int typeChoice = IoTools.getIntFromUserWithMessage("Podaj numer, by wybrać rodzaj zajęć (1 - wykład, 2 - ćwiczenia, 3- warsztaty, 4 - wszystkie): ");
        activityTypeDecide(typeChoice, activities);
    }

    private ActivitiesType choosingActivityType() {

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

                return ActivitiesType.LECTURE;

            case "ćwiczenia":

                return ActivitiesType.EXCERCISE;

            case "warsztaty":

                return ActivitiesType.WORKSHOP;

            default:
                System.out.println("Podano błędne zajęcia. Podaj prawidłową nazwę: ");
                return null;
        }
    }


    private void activityTypeDecide(int typeChoice, List<Activity> activities) {
        switch (typeChoice) {
            case 1: {
                Predicate<Activity> filter = a -> a.getActivitiesType().equals(ActivitiesType.LECTURE);
                printFilteredActivities(filter, activities);
                break;
            }
            case 2: {
                Predicate<Activity> filter = a -> a.getActivitiesType().equals(ActivitiesType.EXCERCISE);
                printFilteredActivities(filter, activities);
                break;
            }
            case 3: {
                Predicate<Activity> filter = a -> a.getActivitiesType().equals(ActivitiesType.WORKSHOP);
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
        int planID = IoTools.getIntFromUserWithMessage("Podaj ID planu, który chcesz edytować:");
        if (!organization.getAllPlansIDs().contains(planID)) {
            System.out.println("Nie ma planu o takim ID!");
            return;
        }
        String name = IoTools.getStringFromUserWithMessage("Podaj nową nazwę zajęć:");
        if (organization.editPlan(planID, name)) {
            System.out.println("Zedytowano plan.");
        }
    }

    public void editActivity() {
        if (organization.getAllActivities().isEmpty()) {
            System.out.println("Nie ma obecnie żadnych zajęć, które można by edytować.");
            return;
        }
        printActivities();
        int activityID = IoTools.getIntFromUserWithMessage("Podaj ID zajęć, które chcesz edytować:");
        if (!organization.getAllActivitiesIDs().contains(activityID)) {
            System.out.println("Nie ma zajęć o takim ID.");
            return;
        }
        System.out.println("Poprzednia nazwa" + organization.getActivity(activityID).getName());
        String name = IoTools.getStringFromUserWithMessage("Podaj nazwę zajęć:");
        short maxUsers = IoTools.getShortFromUserWithMessage("Podaj maksymalną liczbę użytkowników zajęć:");
        byte lenghtInQuarters = IoTools.getByteFromUserWithMessage("Podaj czas trwania zajęć wyrażony w kwadransach:");
        ActivitiesType chosenActivity = choosingActivityType();
        if (organization.editActivity(activityID, name, maxUsers, lenghtInQuarters, chosenActivity)) {
            System.out.println("Zedytowano zajęcia");
        }
    }
}