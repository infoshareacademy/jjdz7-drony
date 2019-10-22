package com.korpodrony.service;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.dao.OrganizationRepositoryDaoImpl;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;
import com.korpodrony.utils.IoTools;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlansService {
    private OrganizationRepositoryDao organization = new OrganizationRepositoryDaoImpl();
    private ActivitiesService aS = new ActivitiesService();

    public void searchByScheduleMenu() {
        System.out.println("- Wpisz nazwę:");
        String searchedText = IoTools.getCharsOnlyStringFromUser().toLowerCase();
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

        aS.getActivitiesByIDs(organization.getPlan(planID).getActivitiesID()).forEach(System.out::println);
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

    private int choosePlan() {
        printPlans();
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu:");
        if (organization.hasPlanWithThisID(choice)) {
            return choice;
        } else {
            System.out.println("Nie ma planu o takim ID.");
            return aS.chooseActivity();
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
}
