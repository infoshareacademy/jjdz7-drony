package com.korpodrony.model;

import java.util.HashSet;
import java.util.Set;
public class Activity {
    private static int currentID = 0;
    private int id;
    private String name;
    private short maxUsers;
    private Set<Integer> assignedUsersIDs;
    private byte lenghtInQuarters;
    private ActivitiesType activitiesType;

    public Activity(String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        this(++currentID, name, maxUsers, new HashSet<>(), lenghtInQuarters, activitiesType);
    }

    public Activity(int id, String name, short maxUsers, Set<Integer> assignedUsersIDs, byte lenghtInQuarters, ActivitiesType activitiesType) {
        this.id = id;
        this.name = name;
        this.maxUsers = maxUsers;
        this.assignedUsersIDs = assignedUsersIDs;
        this.lenghtInQuarters = lenghtInQuarters;
        this.activitiesType = activitiesType;
    }

    public Activity() {
    }

    public static int getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(Set<Activity> activities) {
        int maxValue = 0;
        for (Activity activity : activities) {
            if (activity.getId() > maxValue) {
                maxValue = activity.getId();
            }
        }
        setCurrentID(maxValue);
    }

    public static void setCurrentID(int currentID) {
        Activity.currentID = currentID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(short maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Set<Integer> getAssignedUsersIDs() {
        return assignedUsersIDs;
    }

    public void setAssignedUsersIDs(Set<Integer> assignedUsersIDs) {
        this.assignedUsersIDs = assignedUsersIDs;
    }

    public byte getLenghtInQuarters() {
        return lenghtInQuarters;
    }

    public void setLenghtInQuarters(byte lenghtInQuarters) {
        this.lenghtInQuarters = lenghtInQuarters;
    }

    public ActivitiesType getActivitiesType() {
        return activitiesType;
    }

    public void setActivitiesType(ActivitiesType activitiesType) {
        this.activitiesType = activitiesType;
    }

    public void editActivity(String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        setName(name);
        setMaxUsers(maxUsers);
        setLenghtInQuarters(lenghtInQuarters);
        setActivitiesType(activitiesType);
    }

    public boolean assignUser(int userID) {
        if (canAssignUser(userID)) {
            assignedUsersIDs.add(userID);
            return true;
        }
        return false;
    }

    public boolean assignUser(User user) {
        return assignUser(user.getId());
    }

    public boolean unassignUser(int userID) {
        if (!assignedUsersIDs.contains(userID)) {
            return false;
        }
        assignedUsersIDs.remove(userID);
        return true;
    }

    public boolean unassignUser(User user) {
        return unassignUser(user.getId());
    }

    public boolean canAssignUser(int userID) {
        if (assignedUsersIDs.contains(userID) || assignedUsersIDs.size() == maxUsers) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Zajęcia: " + "id = " + id +
                ", nazwa: " + name +
                ", maksymalna liczba użytkowników: " + maxUsers +
                ", ID przypisanych użytkowników: " + assignedUsersIDs +
                ", czas trwania [min]:" + lenghtInQuarters * 15 +
                ", typ zajęć: " + activitiesType.polishName;
    }
}