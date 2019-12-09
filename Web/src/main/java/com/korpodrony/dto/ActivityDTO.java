package com.korpodrony.dto;

import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import java.util.Set;

public class ActivityDTO {
    int id;
    String name;
    private short maxUsers;
    private Set<Integer> assignedUsersIDs;
    private byte lengthInQuarters;
    private ActivitiesType activitiesType;

    public ActivityDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActivityDTO(int id, String name, short maxUsers, Set<Integer> assignedUsersIDs, byte lengthInQuarters, ActivitiesType activitiesType) {
        this.id = id;
        this.name = name;
        this.maxUsers = maxUsers;
        this.assignedUsersIDs = assignedUsersIDs;
        this.lengthInQuarters = lengthInQuarters;
        this.activitiesType = activitiesType;
    }

    public Activity createActivity(){
        Activity activity = new Activity();
        activity.setId(id);
        activity.setMaxUsers(maxUsers);
        activity.setLengthInQuarters(lengthInQuarters);
        activity.setActivitiesType(activitiesType);
        activity.setAssignedUsersIDs(assignedUsersIDs);
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

    public byte getLengthInQuarters() {
        return lengthInQuarters;
    }

    public void setLengthInQuarters(byte lengthInQuarters) {
        this.lengthInQuarters = lengthInQuarters;
    }

    public ActivitiesType getActivitiesType() {
        return activitiesType;
    }

    public void setActivitiesType(ActivitiesType activitiesType) {
        this.activitiesType = activitiesType;
    }
}
