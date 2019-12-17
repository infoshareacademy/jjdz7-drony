package com.korpodrony.entity;

import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Activity")
@Table(name = "activities")
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private short maxUsers;
    private byte lengthInQuarters;

    @Enumerated(EnumType.STRING)
    private ActivitiesType activitiesType;

    public ActivitiesType getActivitiesType() {
        return activitiesType;
    }

    public void setActivitiesType(ActivitiesType activitiesType) {
        this.activitiesType = activitiesType;
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "activities_users", joinColumns = {@JoinColumn(name = "activity_id",
            referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "user_id",
            referencedColumnName = "id")})
    private Set<UserEntity> assigned_users;

    public ActivityEntity() {
    }

    public Activity createActivity() {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setMaxUsers(maxUsers);
        activity.setLengthInQuarters(lengthInQuarters);
        activity.setActivitiesType(activitiesType);
        if (assigned_users != null) {
            activity.setAssignedUsers(assigned_users.stream()
                    .map(UserEntity::getUserFromEntity
                    ).collect(Collectors.toSet()));
        }
        activity.setAssignedUsersIDs(new HashSet<>());
        return activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int activity_id) {
        this.id = activity_id;
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

    public Set<UserEntity> getAssigned_users() {
        return assigned_users;
    }

    public void setAssigned_users(Set<UserEntity> assigned_users) {
        this.assigned_users = assigned_users;
    }

    public byte getLengthInQuarters() {
        return lengthInQuarters;
    }

    public void setLengthInQuarters(byte lengthInQuarters) {
        this.lengthInQuarters = lengthInQuarters;
    }

    @Override
    public String toString() {
        return "ActivityEntity{" +
                "activity_id=" + id +
                ", name='" + name + '\'' +
                ", maxUsers=" + maxUsers +
                ", lengthInQuarters=" + lengthInQuarters +
                ", assigned_users=" + assigned_users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityEntity that = (ActivityEntity) o;
        return id == that.id &&
                maxUsers == that.maxUsers &&
                lengthInQuarters == that.lengthInQuarters &&
                Objects.equals(name, that.name) &&
                activitiesType == that.activitiesType &&
                Objects.equals(assigned_users, that.assigned_users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxUsers, lengthInQuarters, activitiesType, assigned_users);
    }
}
