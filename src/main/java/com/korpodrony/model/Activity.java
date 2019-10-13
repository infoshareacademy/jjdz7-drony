package com.korpodrony.model;

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

    public Set<Integer> getAssignedUsersIDs() {
        return assignedUsersIDs;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxUsers(short maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setDuration(byte duration) {
        this.duration = duration;
    }

    public short getMaxUsers() {
        return maxUsers;
    }

    @Override
    public String toString() {
        return "ID = " + ID +
                ", nazwa: " + name +
                ", maksymalna liczba użytkowników: " + maxUsers +
                ", ID przypisanych użytkowników: " + assignedUsersIDs +
                ", czas trwania [min]:" + duration*15;
    }
}
