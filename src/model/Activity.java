package model;

import exceptions.*;
import utils.ArrayService;

public class Activity {
    private static int currentID = 0;
    private int activityID;
    private String name;
    private short maxUsers;
    private Integer[] assignedUsersIDs;
    //    Trainer trainer;
    private byte duration; /*Unit of duration is quarter*/

    public Activity(String name, short maxUsers, byte duration) {
        this(++currentID, name, maxUsers, new Integer[0], duration);
    }

    public Activity(int activityID, String name, short maxUsers, Integer[] assignedUsersIDs, byte duration) {
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
        try {
            assignedUsersIDs = ArrayService.addToArray(assignedUsersIDs, user.getUserID(), maxUsers);
            return true;
        } catch (ElementIsNotUniqueException | ArrayLimitReachedException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean unassignUser(User user) {
        try {
            assignedUsersIDs = ArrayService.removeElement(assignedUsersIDs, user.getUserID());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
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
