package com.korpodrony.services;

import com.google.gson.Gson;
import com.korpodrony.dao.OrganizationRepositoryDao;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.stream.Collectors;

@RequestScoped
public class SearchWebService {
    @EJB
    OrganizationRepositoryDao dao;
    private Gson gson = new Gson();

    public String getUsersByName(String name) {
        return gson.toJson(dao.getAllUsers()
                .stream()
                .filter(x -> x.getName()
                        .startsWith("name"))
                .collect(Collectors.toList()));
    }

    public String getActivitiesByName(String name) {
        return gson.toJson(dao.getAllActivities()
                .stream()
                .filter(x -> x.getName()
                        .startsWith("name"))
                .collect(Collectors.toList()));
    }

    public String getPlansByName(String name) {
        return gson.toJson(dao.getAllPlans()
                .stream()
                .filter(x -> x.getName()
                        .startsWith("name"))
                .collect(Collectors.toList()));
    }
}