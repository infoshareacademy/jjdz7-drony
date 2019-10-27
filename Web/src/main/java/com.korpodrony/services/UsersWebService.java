package com.korpodrony.services;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class UsersWebService {

    @EJB
    OrganizationRepositoryDao dao;

    public List<User> getAllUsers(){
        List<User> users = dao.getAllUsers();
        users.sort(new UserIDComparator());
        return users;
    }

}
