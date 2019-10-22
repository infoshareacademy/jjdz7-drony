package com.korpodrony.dao;

import com.korpodrony.model.*;
import com.korpodrony.repository.OrganizationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrganizationRepositoryDaoImpl implements OrganizationRepositoryDao {

    private Organization org = OrganizationRepository.getOrganizationRepository();

    private boolean addUser(User user) {
        if (hasUser(user)) {
            return false;
        }
        org.getUsers()
                .add(user);
        return true;
    }

    private boolean addPlan(Plan plan) {
        if (hasPlan(plan)) {
            return false;
        }
        org.getPlans()
                .add(plan);
        return true;
    }

    private boolean addActivity(Activity activity) {
        if (hasActivity(activity)) {
            return false;
        }
        org.getActivities()
                .add(activity);
        return true;
    }

    private boolean removeUser(int userID) {
        if (!hasUserWithThisID(userID)) {
            return false;
        }
        org.getUsers().remove(getUser(userID));
        return true;
    }

    private boolean removePlan(int planID) {
        if (!hasPlanWithThisID(planID)) {
            return false;
        }
        org.getPlans()
                .remove(getPlan(planID));
        return true;
    }

    private boolean removeActivity(int activityID) {
        if (!hasActivityWithThisID(activityID)) {
            return false;
        }
        org.getActivities()
                .remove(getActivity(activityID));
        return true;
    }

    public boolean createUser(String name, String surname) {
        return addUser(new User(name, surname));
    }

    public boolean createActivity(String name, short maxUsers, byte duration, ActivitiesType activitiesType) {
        return addActivity(new Activity(name, maxUsers, duration, activitiesType));
    }

    public boolean createPlan(String name) {
        return addPlan(new Plan(name));
    }

    public boolean assignUserToActivity(int userID, int activityID) {
        if (!hasUserWithThisID(userID) || !hasActivityWithThisID(activityID)) {
            return false;
        }
        return getActivity(activityID).assignUser(userID);
    }

    public boolean unassignUserFromActivity(int userID, int activityID) {
        if (!hasUserWithThisID(userID) || !hasActivityWithThisID(activityID)) {
            return false;
        }
        return getActivity(activityID).unassignUser(userID);
    }

    public boolean assignActivityToPlan(int activityID, int planID) {
        if (!hasPlanWithThisID(planID) || !hasActivityWithThisID(activityID)) {
            return false;
        }
        return getPlan(planID).assignActivity(activityID);
    }

    public boolean unassignActivityFromPlan(int activityID, int planID) {
        if (!hasPlanWithThisID(planID) || !hasActivityWithThisID(activityID)) {
            return false;
        }
        return getPlan(planID).unassignActivity(activityID);
    }

    public boolean deleteUser(int userID) {
        for (int activityIndex : getAllActivitiesIDs()) {
            unassignUserFromActivity(userID, activityIndex);
        }
        return removeUser(userID);
    }

    public boolean deleteActivity(int activityID) {
        for (int planIndex : getAllPlansIDs()) {
            unassignActivityFromPlan(activityID, planIndex);
        }
        return removeActivity(activityID);
    }

    public boolean deletePlan(int planID) {
        return removePlan(planID);
    }

    public User getUser(int userID) {
        for (User user : org.getUsers()) {
            if (user.getId() == userID) {
                return user;
            }
        }
        return null;
    }

    public Activity getActivity(int activityID) {
        for (Activity activity : org.getActivities()) {
            if (activity.getID() == activityID) {
                return activity;
            }
        }
        return null;
    }

    public Plan getPlan(int planID) {
        for (Plan plan : org.getPlans()) {
            if (plan.getID() == planID) {
                return plan;
            }
        }
        return null;
    }

    public boolean editUser(int userID, String name, String surname) {
        if (!hasUserWithThisID(userID)) {
            return false;
        }
        getUser(userID).editUser(name, surname);
        return true;
    }

    public boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        if (!hasActivityWithThisID(activityID)) {
            return false;
        }
        getActivity(activityID).editActivity(name, maxUsers, lenghtInQuarters, activitiesType);
        return true;
    }

    public boolean editPlan(int planID, String name) {
        if (!hasPlanWithThisID(planID)) {
            return false;
        }
        getPlan(planID).editPlan(name);
        return true;
    }

    public List<Integer> getAllUsersIDs() {
        List<Integer> usersIDs = new ArrayList<>();
        for (User user : org.getUsers()) {
            usersIDs.add(user.getId());
        }
        return usersIDs;
    }

    public List<Integer> getAllActivitiesIDs() {
        List<Integer> activitiesID = new ArrayList<>();
        for (Activity activity : org.getActivities()) {
            activitiesID.add(activity.getID());
        }
        return activitiesID;
    }

    public List<Integer> getAllPlansIDs() {
        List<Integer> plansIDs = new ArrayList<>();
        for (Plan plan : org.getPlans()) {
            plansIDs.add(plan.getID());
        }
        return plansIDs;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(org.getUsers());
    }

    public List<Activity> getAllActivities() {
        return new ArrayList<>(org.getActivities());
    }

    public List<Plan> getAllPlans() {
        return new ArrayList<>(org.getPlans());
    }

    public boolean hasUser(User user) {
        for (User u : org.getUsers()) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActivity(Activity activity) {
        for (Activity a : org.getActivities()) {
            if (a.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlan(Plan plan) {
        for (Plan p : org.getPlans()) {
            if (p.equals(plan)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUserWithThisID(int userID) {
        for (User user : org.getUsers()) {
            if (user.getId() == userID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActivityWithThisID(int activityID) {
        for (Activity activity : org.getActivities()) {
            if (activity.getID() == activityID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlanWithThisID(int planID) {
        for (Plan plan : org.getPlans()) {
            if (plan.getID() == planID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<User> getUsersSet() {
        return org.getUsers();
    }

    @Override
    public Set<Activity> getActivitiesSet() {
        return org.getActivities();
    }

    @Override
    public Set<Plan> getPlansSet() {
        return org.getPlans();
    }
}