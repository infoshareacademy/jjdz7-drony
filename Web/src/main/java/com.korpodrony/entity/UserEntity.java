package com.korpodrony.entity;

import com.korpodrony.model.User;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "assigned_users")
    private Set<ActivityEntity> users_activities;

    public UserEntity(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserEntity() {
    }

    public User getUserFromEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setSurname(this.surname);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return name.equals(that.name) &&
                surname.equals(that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    public int getId() {
        return id;
    }

    public void setId(int user_id) {
        this.id = user_id;
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

    public Set<ActivityEntity> getUsers_activities() {
        return users_activities;
    }

    public void setUsers_activities(Set<ActivityEntity> users_activities) {
        this.users_activities = users_activities;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}