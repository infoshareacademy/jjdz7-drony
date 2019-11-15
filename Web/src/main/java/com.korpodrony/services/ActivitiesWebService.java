package com.korpodrony.services;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequestScoped
public class ActivitiesWebService {

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

    public List<Activity> getAllActivities() {
        List<Activity> activities = dao.getAllActivities();
        activities.sort(new ActivityIDComparator());
        return activities;
    }

    public List<Activity> getAllActivities(Predicate<Activity> predicate) {
        List<Activity> activities = dao.getAllActivities();
        activities.sort(new ActivityIDComparator());
        return (List<Activity>) activities.stream()
                .filter(predicate).collect(Collectors.toList());
    }

    public boolean assignUserToActivity(int userId, int activityId) {
        return dao.assignUserToActivity(userId, activityId);
    }

    public boolean unassignUserFromActivity(int userId, int activityId) {
        return dao.unassignUserFromActivity(userId, activityId);
    }

    public boolean deleteActivity(int activityId) {
        if (dao.deleteActivity(activityId)){
            setActivityIdToLastValue();
            return true;
        }
        return false;
    }

    private void setActivityIdToLastValue() {
        int currentId = dao.getAllActivities()
                .stream()
                .map(Activity::getId).max(Comparator.comparingInt(x -> x))
                .orElse(0);
        Activity.setCurrentID(currentId);
    }

    public boolean editActivity(int activityId, String name, short maxUsers, byte duration, int activityTypeNumber) {
        return dao.editActivity(activityId, name, maxUsers, duration, ActivitiesType.getActivity(activityTypeNumber));
    }

    public boolean createActivity(String name, short maxUsers, byte duration, int activityType) {
        return dao.createActivity(name, maxUsers, duration, ActivitiesType.getActivity(activityType));
    }
}
