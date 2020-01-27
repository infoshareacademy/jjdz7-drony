package com.korpodrony.services;

import com.korpodrony.daoInterfaces.ReportsStatisticsDaoInterface;
import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.Action;
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

    public int findUserIdByEmail(String email) {
        return userRepositoryDao.getUserIdByEmail(email);
    }

    public int createUser(String name, String surname, String email) {
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.USERS, Action.ADD);
        return userRepositoryDao.createUser(name, surname, email);
    }

    public UserDTO findUserDTOByEmail(String email) {
        currentUserService.setEmail(email);
        return userRepositoryDao.getUserDTO(email);
    }
}