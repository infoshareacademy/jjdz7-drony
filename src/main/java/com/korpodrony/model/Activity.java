package com.korpodrony.model;

import com.korpodrony.service.ActivitiesType;

import java.util.HashSet;
import java.util.Set;

public class Activity {
    private static int currentID = 0;
    private int ID;
    private String name;
    private short maxUsers;
    private Set<Integer> assignedUsersIDs;
    //    Trainer trainer;
    private byte duration; /*Unit of duration is quarter*/
    private ActivitiesType activitiesType;

    public Activity(String name, short maxUsers, byte duration) {
        this(++currentID, name, maxUsers, new HashSet<>(), duration);
    }

    public Activity(int ID, String name, short maxUsers, Set<Integer> assignedUsersIDs, byte duration) {
        this.ID = ID;
        this.name = name;
        this.maxUsers = maxUsers;
        this.assignedUsersIDs = assignedUsersIDs;
        this.duration = duration;
    }

    public Activity() {
    }

    public static int getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(Set<Activity>activities){
        int maxValue = 0;
        for (Activity activity: activities){
            if (activity.getID()>maxValue){
                maxValue=activity.getID();
            }
        }
        setCurrentID(maxValue);
    }

    public static void setCurrentID(int currentID) {
        Activity.currentID = currentID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public byte getDuration() {
        return duration;
    }

    public void setDuration(byte duration) {
        this.duration = duration;
    }

    public ActivitiesType getActivitiesType() {
        return activitiesType;
    }

    public void setActivitiesType(ActivitiesType activitiesType) {
        this.activitiesType = activitiesType;
    }

    public void editActivity(String name, short maxUsers, byte duration) {
        setName(name);
        setMaxUsers(maxUsers);
        setDuration(duration);
    }

    public boolean assignUser(int userID) {
        if (canAssignUser(userID)) {
            assignedUsersIDs.add(userID);
            return true;
        }
        return false;
    }

    public boolean assignUser(User user) {
        return assignUser(user.getID());
    }

    public boolean unassignUser(int userID) {
        if (!assignedUsersIDs.contains(userID)) {
            return false;
        }
        assignedUsersIDs.remove(userID);
        return true;
    }

    public boolean canAssignUser(int userID) {
        if (assignedUsersIDs.contains(userID) || assignedUsersIDs.size() == maxUsers) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Zajęcia: "+ "ID = " + ID +
                ", nazwa: " + name +
                ", maksymalna liczba użytkowników: " + maxUsers +
                ", ID przypisanych użytkowników: " + assignedUsersIDs +
                ", czas trwania [min]:" + duration*15;
    }
}
