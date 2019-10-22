package com.korpodrony.service;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.dao.OrganizationRepositoryDaoImpl;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;
import com.korpodrony.model.User;
import com.korpodrony.utils.IoTools;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ActivitiesService {
    private OrganizationRepositoryDao organization = new OrganizationRepositoryDaoImpl();
    private UserService uS = new UserService();

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
        uS.getUsersByIDs(organization.getActivity(activityID).getAssignedUsersIDs()).forEach(System.out::println);
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

    public void searchByActivityMenu() {
        System.out.println("- Wpisz nazwę:");
        String searchedText = IoTools.getCharsOnlyStringFromUser().toLowerCase();
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

    List<Activity> getActivitiesByIDs(Set<Integer> activities) {
        List<Activity> resultActivity = new ArrayList<>();
        List<Integer> activitiesIDs = new ArrayList<>(activities);
        Collections.sort(activitiesIDs);
        for (int i : activitiesIDs) {
            resultActivity.add(organization.getActivity(i));
        }
        return resultActivity;
    }


    public void printActivities() {
        printActivities(new ActivityIDComparator());
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

    int chooseActivity() {
        printActivities();
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID zajęć:");
        if (organization.hasActivityWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma zajęć o takim ID.");
            return chooseActivity();
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
}
