package com.korpodrony.entity;

import com.korpodrony.dto.UserDTO;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "User")
@Table(name = "users")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    @ColumnDefault(value = "'USER'")
    @Enumerated(EnumType.STRING)
    private PermissionLevel permissionLevel;

    public UserEntity(String name, String surname, PermissionLevel permissionLevel) {
        this.name = name;
        this.surname = surname;
        this.permissionLevel = permissionLevel;
    }

    public UserEntity() {
    }

    public UserDTO createUserDTO() {
        return new UserDTO(id, name, surname);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", permissionLevel=" + permissionLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return name.equals(that.name) &&
                surname.equals(that.surname) &&
                permissionLevel == that.permissionLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, permissionLevel);
    }
}