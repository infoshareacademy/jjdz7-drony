package com.korpodrony.services;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.User;
import com.korpodrony.utils.JSONWriter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequestScoped
public class SearchWebService {
    @EJB
    OrganizationRepositoryDao dao;

    public String getUsersByName(String name) {
        return JSONWriter.generateJsonString(
                dao.getAllUsers()
                        .stream()
                        .filter(indetifyUserByText(name))
                        .sorted((x, y) -> new UserIDComparator().compare(x, y))
                        .collect(Collectors.toList()));
    }

    public String getActivitiesByName(String name) {
        return JSONWriter.generateJsonString(dao.getAllActivities()
                .stream()
                .filter(x -> x.getName()
                        .toLowerCase()
                        .contains(name))
                .sorted((x, y) -> new ActivityIDComparator().compare(x, y))
                .collect(Collectors.toList()));
    }

    public String getPlansByName(String name) {
        return JSONWriter.generateJsonString(
                dao.getAllPlans()
                        .stream()
                        .filter(x -> x.getName()
                                .toLowerCase()
                                .contains(name)
                        )
                        .sorted((x, y) -> new PlanIDComparator().compare(x, y))
                        .collect(Collectors.toList()));
    }

    private Predicate<User> indetifyUserByText(String name) {
        return x ->(x.getName() + " " + x.getSurname())
                .toLowerCase()
                .contains(name);
    }
}