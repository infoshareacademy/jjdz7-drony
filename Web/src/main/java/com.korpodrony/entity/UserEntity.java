package com.korpodrony.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users")
public class UserEntity {
    @ManyToMany(mappedBy = "assigned_users")
    List<ActivityEntity> usersActivities;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String name;
    private String surname;

    @ManyToMany(mappedBy = "assigned_users")
    private Set<ActivityEntity> users_activities;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<ActivityEntity> getUsersActivities() {
        return usersActivities;
    }

    public void setUsersActivities(List<ActivityEntity> usersActivities) {
        this.usersActivities = usersActivities;
    }

    public Set<ActivityEntity> getUsers_activities() {
        return users_activities;
    }

    public void setUsers_activities(Set<ActivityEntity> users_activities) {
        this.users_activities = users_activities;
    }
}
