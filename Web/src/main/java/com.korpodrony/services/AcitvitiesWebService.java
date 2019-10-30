package com.korpodrony.services;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.Activity;
import com.korpodrony.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class AcitvitiesWebService {

    @EJB
    OrganizationRepositoryDao dao;

    @Inject
    UsersWebService usersWebService;

    public boolean hasActivity(int activityId) {
        return dao.hasActivityWithThisID(activityId);
    }

    public Activity getActivity(int activityId) {
        return dao.getActivity(activityId);
    }

    public List<User> getAssignedUsers(int activityId) {
        return getActivity(activityId)
                .getAssignedUsersIDs()
                .stream()
                .map(x -> dao.getUser(x))
                .sorted((x, y) -> x.getId() - y.getId())
                .collect(Collectors.toList());
    }

    public List<User> getAvaiableUsers(int activityId) {
        return usersWebService
                .getAllUsers()
                .stream()
                .filter(x -> !getAssignedUsers(activityId).contains(x))
                .collect(Collectors.toList());
    }

    public Object getAllActivities() {
        List<Activity> activities = dao.getAllActivities();
        activities.sort(new ActivityIDComparator());
        return activities;
    }
}
