package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.AuthUserDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.PermissionLevel;
import com.korpodrony.entity.UserEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserRepositoryDaoInterface {

    int createUser(String name, String surname, String email, PermissionLevel permissionLevel);

    AuthUserDTO getAuthUserDTO(String email);

    List<UserDTO> getAllUsers();

    List<UserDTO> getUserDTObyName(String name);

    List<UserDTO> getUserDTObyName(String name, String surname);

    void updateUserPermissionLevel(int userId, PermissionLevel permissionLevel);

    int getUserIdByEmail(String email);

    List<UserEntity> getAllUsersEntities();
}