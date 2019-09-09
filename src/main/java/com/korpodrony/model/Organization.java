package com.korpodrony.model;

import java.util.*;

public class Organization {
    public Set<User> users;
    public Set<Plan> plans;
    public Set<Activity> activities;

    public Organization(Set<User> users, Set<Plan> plans, Set<Activity> activities) {
        this.users = users;
        this.plans = plans;
        this.activities = activities;
    }

    public Organization() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    private boolean addUser(User user) {
        if (hasUser(user)) {
            return false;
        }
        users.add(user);
        return true;
    }

    private boolean addPlan(Plan plan) {
        if (hasPlan(plan)) {
            return false;
        }
        plans.add(plan);
        return true;
    }

    private boolean addActivity(Activity activity) {
        if (hasActivity(activity)) {
            return false;
        }
        activities.add(activity);
        return true;
    }

    private boolean removeUser(int userID) {
        if (!hasUserWithThisID(userID)) {
            return false;
        }
        users.remove(userID);
        return true;
    }

    private boolean removePlan(int planID) {
        if (!hasPlanWithThisID(planID)) {
            return false;
        }
        plans.remove(getPlan(planID));
        return true;
    }

    private boolean removeActivity(int activityID) {
        if (!hasActivityWithThisID(activityID)) {
            return false;
        }
        activities.remove(getActivity(activityID));
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
        for (User user : users) {
            if (user.getID() == userID) {
                return user;
            }
        }
        return null;
    }

    public Activity getActivity(int activityID) {
        for (Activity activity : activities) {
            if (activity.getID() == activityID) {
                return activity;
            }
        }
        return null;
    }

    public Plan getPlan(int planID) {
        for (Plan plan : plans) {
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

    public boolean editActivity(int activityID, String name, short maxUsers, byte duration) {
        if (!hasActivityWithThisID(activityID)) {
            return false;
        }
        getActivity(activityID).editActivity(name, maxUsers, duration);
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
        for (User user : users) {
            usersIDs.add(user.getID());
        }
        return usersIDs;
    }

    public List<Integer> getAllActivitiesIDs() {
        List<Integer> activitiesID = new ArrayList<>();
        for (Activity activity : activities) {
            activitiesID.add(activity.getID());
        }
        return activitiesID;
    }

    public List<Integer> getAllPlansIDs() {
        List<Integer> activitiesID = new ArrayList<>();
        for (Activity activity : activities) {
            activitiesID.add(activity.getID());
        }
        return activitiesID;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public List<Activity> getAllActivies() {
        return new ArrayList<>(activities);
    }

    public List<Plan> getAllPlans() {
        return new ArrayList<>(plans);
    }

    public boolean hasUser(User user) {
        for (User u : users) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActivity(Activity activity) {
        for (Activity a : activities) {
            if (a.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlan(Plan plan) {
        for (Plan p : plans) {
            if (p.equals(plan)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasUserWithThisID(int userID) {
        for (User user : users) {
            if (user.getID() == userID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActivityWithThisID(int activityID) {
        for (Activity activity : activities) {
            if (activity.getID() == activityID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlanWithThisID(int planID) {
        for (Plan plan : plans) {
            if (plan.getID() == planID) {
                return true;
            }
        }
        return false;
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