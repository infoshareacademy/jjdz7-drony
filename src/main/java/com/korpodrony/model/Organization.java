package com.korpodrony.model;

import java.util.*;

public class Organization {
    private Map<Integer, User> users;
    private Map<Integer, Plan> plans;
    private Map<Integer, Activity> activities;

    public Organization(Map<Integer, User> users, Map<Integer, Plan> plans, Map<Integer, Activity> activities) {
        this.users = users;
        this.plans = plans;
        this.activities = activities;
    }

    public Organization() {
        this(new HashMap<Integer, User>(), new HashMap<Integer, Plan>(), new HashMap<Integer, Activity>());
    }

    private boolean addUser(User user) {
        if (hasUser(user.getUserID())) {
            return false;
        }
        users.put(user.getUserID(), user);
        return true;
    }

    private boolean addPlan(Plan plan) {
        if (hasPlan(plan.getPlanID())) {
            return false;
        }
        plans.put(plan.getPlanID(), plan);
        return true;
    }

    private boolean addActivity(Activity activity) {
        if (hasActivity(activity.getActivityID())) {
            return false;
        }
        activities.put(activity.getActivityID(), activity);
        return true;
    }

    private boolean removeUser(int userID) {
        if (!hasUser(userID)) {
            return false;
        }
        users.remove(userID);
        return true;
    }

    private boolean removePlan(int planID) {
        if (!hasPlan(planID)) {
            return false;
        }
        plans.remove(planID);
        return true;
    }

    private boolean removeActivity(int activityID) {
        if (!hasActivity(activityID)) {
            return false;
        }
        activities.remove(activityID);
        return true;
    }

    public boolean createUser(String name, String surname) {
        return addUser(new User(name, surname));
    }

    public boolean createActivity(String name, short maxUsers, byte duration) {
        return addActivity(new Activity(name, maxUsers, duration));
    }

    public boolean createPlan(Organization organization, String name) {
        return addPlan(new Plan(name));
    }

    public boolean assignUserToActivity(int userID, int activityID) {
        if (!hasUser(userID) || !hasActivity(activityID)) {
            return false;
        }
        return getActivity(activityID).assignUser(userID);
    }

    public boolean unassignUserFromActivity(int userID, int activityID) {
        if (!hasUser(userID) || !hasActivity(activityID)) {
            return false;
        }
        return getActivity(activityID).unassignUser(userID);
    }

    public boolean assignActivityToPlan(int activityID, int planID) {
        if (!hasPlan(planID) || !hasActivity(activityID)) {
            return false;
        }
        return getPlan(planID).assignActivity(activityID);
    }

    public boolean unassignActivityFromPlan(int activityID, int planID) {
        if (!hasPlan(planID) || !hasActivity(activityID)) {
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

    public boolean deletePlan(int planID){
        return removePlan(planID);
    }

    public User getUser(int userID) {
        return users.get(userID);
    }

    public Activity getActivity(int activityID) {
        return activities.get(activityID);
    }

    public Plan getPlan(int planID) {
        return plans.get(planID);
    }

    public boolean editUser(int userID, String name, String surname) {
        if (!users.containsKey(userID)) {
            return false;
        }
        users.get(userID).editUser(name, surname);
        return true;
    }

    public boolean editActivity(int activityID, String name, short maxUsers, byte duration) {
        if (!activities.containsKey(activityID)) {
            return false;
        }
        activities.get(activityID).editActivity(name, maxUsers, duration);
        return true;
    }

    public boolean editPlan(int planID, String name) {
        if (!plans.containsKey(planID)) {
            return false;
        }
        plans.get(planID).editPlan(name);
        return true;
    }

    public Set<Integer> getAllUsersIDs() {
        return users.keySet();
    }

    public Set<Integer> getAllActivitiesIDs() {
        return activities.keySet();
    }

    public Set<Integer> getAllPlansIDs() {
        return plans.keySet();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<Activity> getAllActivies() {
        return new ArrayList<>(activities.values());
    }

    public List<Plan> getAllPlans() {
        return new ArrayList<>(plans.values());
    }

    public boolean hasUser(int userID) {
        return users.containsKey(userID);
    }

    public boolean hasActivity(int activityID) {
        return activities.containsKey(activityID);
    }

    public boolean hasPlan(int planID) {
        return plans.containsKey(planID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(users, that.users) &&
                Objects.equals(plans, that.plans) &&
                Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, plans, activities);
    }
}