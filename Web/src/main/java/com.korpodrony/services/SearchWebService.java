package com.korpodrony.services;

import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.utils.JSONWriter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.stream.Collectors;

@RequestScoped
public class SearchWebService {
    @EJB
    OrganizationRepositoryDao dao;

    public String getUsersByName(String name) {
        return JSONWriter.generateJsonString(
                dao.getAllUsers()
                        .stream()
                        .filter(x -> x.getName()
                                .toLowerCase()
                                .contains(name))
                        .collect(Collectors.toList()));
    }

    public String getActivitiesByName(String name) {
        return JSONWriter.generateJsonString(dao.getAllActivities()
                .stream()
                .filter(x -> x.getName()
                        .toLowerCase()
                        .contains(name))
                .collect(Collectors.toList()));
    }

    public String getPlansByName(String name) {
        return JSONWriter.generateJsonString(
                dao.getAllPlans()
                        .stream()
                        .filter(x -> x.getName()
                                .toLowerCase()
                                .contains(name))
                        .collect(Collectors.toList()));
    }
}