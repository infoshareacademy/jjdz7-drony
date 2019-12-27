package com.korpodrony.services;

import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class UsersWebService {

    @EJB
    UserRepositoryDaoInterface userRepositoryDao;

    public List<UserDTO> getAllUsers() {
        return userRepositoryDao.getAllUsers();
    }
}