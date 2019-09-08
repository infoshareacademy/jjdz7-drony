package com.korpodrony.model;

import java.util.HashSet;
import java.util.Set;

public class Activity {
    private static int currentID = 0;
    private int activityID;
    private String name;
    private short maxUsers;
    private Set<Integer> assignedUsersIDs;
    //    Trainer trainer;
    private byte duration; /*Unit of duration is quarter*/

    public Activity(String name, short maxUsers, byte duration) {
        this(++currentID, name, maxUsers, new HashSet<>(), duration);
    }

    public Activity(int activityID, String name, short maxUsers, Set<Integer> assignedUsersIDs, byte duration) {
        this.activityID = activityID;
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

    public boolean assignUser(User user) {
        if (assignedUsersIDs.contains(user.getUserID()) || assignedUsersIDs.size() == maxUsers) {
            return false;
        }
        assignedUsersIDs.add(user.getUserID());
        return true;
    }

    public boolean assignUser(int index) {
        if (assignedUsersIDs.contains(index) || assignedUsersIDs.size() == maxUsers) {
            return false;
        }
        assignedUsersIDs.add(index);
        return true;
    }

    public boolean unassignUser(User user) {
        if (!assignedUsersIDs.contains(user.getUserID())) {
            return false;
        }
        assignedUsersIDs.remove(user.getUserID());
        return true;
    }

    public boolean unassignUser(int index) {
        if (!assignedUsersIDs.contains(index)) {
            return false;
        }
        assignedUsersIDs.remove(index);
        return true;
    }

    public Set<Integer> getAssignedUsersIDs() {
        return assignedUsersIDs;
    }

    public int getActivityID() {
        return activityID;
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
}
