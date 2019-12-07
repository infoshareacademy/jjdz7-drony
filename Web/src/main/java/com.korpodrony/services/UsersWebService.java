package com.korpodrony.services;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.UserDaoImpl;
import com.korpodrony.dao.UserRepositoryDao;
import com.korpodrony.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class UsersWebService {

    @EJB
    UserRepositoryDao userRepositoryDao;

    public List<User> getAllUsers() {
        return userRepositoryDao.getAllUsers()
                .stream()
                .sorted((x, y) -> new UserIDComparator()
                        .compare(x, y))
                .collect(Collectors.toList());
    }
}