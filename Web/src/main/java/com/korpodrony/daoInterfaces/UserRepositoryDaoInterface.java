package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.UserDTO;
import com.korpodrony.model.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface UserRepositoryDaoInterface {
    boolean createUser(String name, String surname);

    boolean deleteUser(int userID);

    UserDTO getUserDTO(int userID);

    boolean editUser(int userID, String name, String surname);

    List<UserDTO> getAllUsers();

    boolean hasUserWithThisID(int userID);
}