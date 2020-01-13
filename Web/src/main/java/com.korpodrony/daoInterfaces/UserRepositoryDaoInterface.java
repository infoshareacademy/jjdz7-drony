package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.AuthUserDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.PermissionLevel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserRepositoryDaoInterface {

    int createUser(String name, String surname, String email);

    int createUser(String name, String surname, String email, PermissionLevel permissionLevel);

    UserDTO getUserDTO(String email);

    AuthUserDTO getAuthUserDTO(String email);

    List<UserDTO> getAllUsers();

    boolean hasUser(int userID);

    List<UserDTO> getUserDTObyName(String name);

    List<UserDTO> getUserDTObyName(String name, String surname);

    int getUserIdByEmail(String email);
}