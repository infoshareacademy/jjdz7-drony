package com.korpodrony.dto;

import com.korpodrony.model.ActivitiesType;

import java.util.Set;

public class ActivityDTO {
    private int id;
    private String name;
    private short maxUsers;
    private Set<UserDTO> assignedUsers;
    private byte lengthInQuarters;
    private ActivitiesType activitiesType;

    public ActivityDTO(int id, String name, short maxUsers, Set<UserDTO> assignedUsers, byte lengthInQuarters, ActivitiesType activitiesType) {
        this.id = id;
        this.name = name;
        this.maxUsers = maxUsers;
        this.assignedUsers = assignedUsers;
        this.lengthInQuarters = lengthInQuarters;
        this.activitiesType = activitiesType;
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

    public Set<UserDTO> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<UserDTO> assignedUsers) {
        this.assignedUsers = assignedUsers;
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
