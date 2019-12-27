package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.UserEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserRepositoryDaoInterface {

    int createUser(String name, String surname, String email);

    boolean deleteUser(int userID);

    UserDTO getUserDTO(int userID);

    boolean editUser(int userID, String name, String surname);

    List<UserDTO> getAllUsers();

    boolean hasUser(int userID);

    List<UserDTO> getUserDTObyName(String name);

    List<UserDTO> getUserDTObyName(String name, String surname);

    int getUserIdByEmail(String email);
}