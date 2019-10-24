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

import static java.lang.System.out;

public class PlansService {
    private OrganizationRepositoryDao dao = new OrganizationRepositoryDaoImpl();
    private ActivitiesService activitiesService = new ActivitiesService();

    public void searchByScheduleMenu() {
        out.println("- Wpisz nazwę:");
        String searchedText = IoTools.getCharsOnlyStringFromUser().toLowerCase();
        List<Plan> plans = dao.getAllPlans().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedText))
                .collect(Collectors.toList());
        if (checkWithMessageOnFalse(plans.isEmpty(), "Nie ma takiego planu.")) {
            plans.sort(new PlanIDComparator());
            plans.forEach(out::println);
        }
    }

    public void showActivitiesOfSchedule() {
        printPlans();
        if (!dao.getAllPlans().isEmpty()) {
            int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu, którego zajęcia chcesz obejrzeć: ");
            if (checkWithMessageOnFalse(dao.hasPlanWithThisID(choice), "Nie ma takiego planu.")) {
                List<Activity> activities = getAssignedActivities(choice);
                if (checkWithMessageOnFalse(!activities.isEmpty(), "Plan nie ma przypisanych żadnych zajęć!")) {
                    activities.sort(new UserIDComparator());
                    activities.forEach(out::println);
                }
            }
        }
    }

    private List<Activity> getAssignedActivities(int choice) {
        return dao.getPlan(choice)
                .getActivitiesID().stream()
                .map(x -> dao.getActivity(x))
                .collect(Collectors.toList());
    }

    public void addPlan() {
        String name = IoTools.getStringFromUserWithMessage("Podaj nazwę planu:");
        if (dao.createPlan(name)) {
            out.println("Dodano plan.");
        } else {
            out.println("Taki plan juz istnieje!");
        }
    }

    public void assignActivityToPlan() {
        if (arePlansAndActivities()) {
            int planID = choosePlan();
            if (checkWithMessageOnFalse(!chooseAvailableActivities(planID).isEmpty(), "Nie ma obecnie żadnych zajęć, które można przypisać.")) {
                List<Activity> availableActivities = chooseAvailableActivities(planID);
                availableActivities.sort(new ActivityIDComparator());
                availableActivities.forEach(out::println);
                int activityID = IoTools.getIntFromUserWithMessage("Podaj ID zajęć, które chcesz przypisać do planu:");
                if (canAssignActivityToPlan(activityID, planID) && dao.assignActivityToPlan(activityID, planID)) {
                    out.println("Zajęcia przypisano do planu.");
                }
            }
        }
    }

    private boolean canAssignActivityToPlan(int activityID, int planID) {
        return (checkWithMessageOnFalse(dao.hasActivityWithThisID(activityID), "Zajęcia z takim ID nie istnieją!")
                &&
                checkWithMessageOnFalse(!dao.getPlan(planID).getActivitiesID().contains(activityID), "Te zajęcia są już przypisane do tego planu!"));
    }

    public void unassignActivityFromPlan() {
        if (arePlansAndActivities()) {
            int planID = choosePlan();
            if (isPlanWithMessage(planID)) {
                int activityID = getActivityIDToUnAssign(planID);
                if (canUnassignActivityFromPlan(activityID, planID) && dao.unassignActivityFromPlan(activityID, planID)) {
                    out.println("Zajęcia wypisano z planu.");
                }
            }
        }
    }

    private boolean arePlansAndActivities() {
        return checkWithMessageOnFalse(!dao.getAllPlans().isEmpty() || !dao.getAllActivities().isEmpty(),
                "Nie ma obecnie żadnych planów lub zajęć.");
    }

    private boolean isPlanWithMessage(int planID) {
        return checkWithMessageOnFalse(!dao.getPlan(planID).getActivitiesID().isEmpty(),
                "Nie ma obecnie przypisanych żadnych zajęć do planu.");
    }

    private int getActivityIDToUnAssign(int planID) {
        printActivitiesOfPlan(planID);
        return IoTools.getIntFromUserWithMessage("Podaj ID zajęć do wypisania z planu:");
    }

    private void printActivitiesOfPlan(int planID) {
        activitiesService.getActivitiesByIDs(dao.getPlan(planID)
                .getActivitiesID())
                .forEach(out::println);
    }

    private boolean canUnassignActivityFromPlan(int activityID, int planID) {
        return (checkWithMessageOnFalse(dao.getAllActivitiesIDs().contains(activityID), "Zajęcia o takim ID nie istnieją.")
                &&
                checkWithMessageOnFalse(dao.getPlan(planID).getActivitiesID().contains(activityID), "Zajęcia nie są przypisane do planu, więc nie można ich wypisać."));
    }

    private List<Activity> chooseAvailableActivities(int planID) {
        Set<Integer> activitiesFromPlan = dao.getPlan(planID).getActivitiesID();
        return dao.getAllActivities().stream()
                .filter(x -> !activitiesFromPlan.contains(x.getID()))
                .collect(Collectors.toList());
    }

    private int choosePlan() {
        printPlans();
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu:");
        if (dao.hasPlanWithThisID(choice)) {
            return choice;
        } else {
            out.println("Nie ma planu o takim ID.");
            return choosePlan();
        }
    }

    public void removePlan() {
        printPlans();
        if (!dao.getAllPlans().isEmpty()) {
            int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu do usunięcia:");
            if (dao.deletePlan(choice)) {
                out.println("Usunięto plan.");
            } else {
                out.println("Wybrany plan nie istnieje!");
            }
        }
    }

    private void printPlans(Comparator comparator) {
        List<Plan> plans = dao.getAllPlans();
        plans.sort(comparator);
        if (checkWithMessageOnFalse(!plans.isEmpty(), "Nie ma obecnie żadnych planów.")) {
            plans.forEach(out::println);
        }
    }

    public void printPlans() {
        printPlans(new PlanIDComparator());
    }

    public void editPlan() {
        if (checkWithMessageOnFalse(!dao.getAllPlans().isEmpty(), "Nie ma obecnie żadnych planów, które można by edytować.")) {
            printPlans();
            int planID = IoTools.getIntFromUserWithMessage("Podaj ID planu, który chcesz edytować:");
            if (!dao.getAllPlansIDs().contains(planID)) {
                out.println("Nie ma planu o takim ID!");
                return;
            }
            String name = IoTools.getStringFromUserWithMessage("Podaj nową nazwę zajęć:");
            if (dao.editPlan(planID, name)) {
                out.println("Zedytowano plan.");
            }
        }
    }

    private boolean checkWithMessageOnFalse(boolean statement, String s) {
        if (!statement) {
            out.println(s);
            return false;
        }
        return true;
    }
}