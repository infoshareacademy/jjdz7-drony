package com.korpodrony.services;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class UsersWebService {

    @EJB
    OrganizationRepositoryDao dao;

    public List<User> getAllUsers() {
        return dao.getAllUsers()
                .stream()
                .sorted((x, y) -> new UserIDComparator()
                        .compare(x, y))
                .collect(Collectors.toList());
    }
}
