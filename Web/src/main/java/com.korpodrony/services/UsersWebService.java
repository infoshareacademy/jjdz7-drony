package com.korpodrony.services;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.UserDaoImpl;
import com.korpodrony.dao.UserRepositoryDao;
import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class UsersWebService {

    @EJB
    UserRepositoryDaoInterface userRepositoryDao;

    public List<UserDTO> getAllUsers() {
        return userRepositoryDao.getAllUsers()
                .stream()
                .collect(Collectors.toList());
    }
}