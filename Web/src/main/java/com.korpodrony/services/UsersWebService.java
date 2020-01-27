package com.korpodrony.services;

import com.korpodrony.daoInterfaces.ReportsStatisticsDaoInterface;
import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.AuthUserDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.Action;
import com.korpodrony.entity.View;
import com.korpodrony.entity.PermissionLevel;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.entity.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class UsersWebService {

    @EJB
    UserRepositoryDaoInterface userRepositoryDao;

    @Inject
    ReportsStatisticsDaoInterface reportsStatisticsDaoInterface;

    @Inject
    CurrentUserService currentUserService;

    Logger logger = LoggerFactory.getLogger("com.korpodrony.services");

    public List<UserDTO> getAllUsers() {
        logger.debug("getAllUsers called");
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.USERS, Action.GET_AVAILABLE_LIST);
        return userRepositoryDao.getAllUsers();
    }

    public List<UserEntity> getAllUserEntities() {
        logger.debug("getAllUsers called");
        return userRepositoryDao.getAllUsersEntities();
    }

    public int findUserIdByEmail(String email) {
        return userRepositoryDao.getUserIdByEmail(email);
    }

    public int createUser(String name, String surname, String email, PermissionLevel permissionLevel) {
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.USERS, Action.ADD);
        return userRepositoryDao.createUser(name, surname, email, permissionLevel);
    }

    public void updatePermissionLevel(int userId, PermissionLevel level) {
        userRepositoryDao.updateUserPermissionLevel(userId, level);
    }

    public AuthUserDTO findAuthUserDTOByEmail(String email) {
        currentUserService.setEmail(email);
        return userRepositoryDao.getAuthUserDTO(email);
    }
}