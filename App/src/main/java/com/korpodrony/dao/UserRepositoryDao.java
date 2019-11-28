package com.korpodrony.dao;

import com.korpodrony.model.User;

import java.util.List;
import java.util.Set;

public interface UserRepositoryDao {
    boolean createUser(String name, String surname);

    boolean deleteUser(int userID);

    User getUser(int userID);

    boolean editUser(int userID, String name, String surname);

    List<Integer> getAllUsersIDs();

    List<User> getAllUsers();

    boolean hasUser(User user);

    boolean hasUserWithThisID(int userID);

    Set<User> getUsersSet();
}
