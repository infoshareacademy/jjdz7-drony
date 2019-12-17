package com.korpodrony.services;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.dao.ActivityRepositoryDao;
import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequestScoped
public class ActivitiesWebService {

    @EJB
    ActivityRepositoryDao activityRepositoryDao;

    public boolean hasActivity(int activityId) {
        return activityRepositoryDao.hasActivityWithThisID(activityId);
    }

    public List<Activity> getAllActivities() {
        List<Activity> activities = activityRepositoryDao.getAllSimplifiedActivities();
        activities.sort(new ActivityIDComparator());
        return activities;
    }

    public Activity getActivity(int activityId){
        return activityRepositoryDao.getActivity(activityId);
    }

    public boolean assignUserToActivity(int userId, int activityId) {
        return activityRepositoryDao.assignUserToActivity(userId, activityId);
    }

    public boolean unassignUserFromActivity(int userId, int activityId) {
        return activityRepositoryDao.unassignUserFromActivity(userId, activityId);
    }

    public boolean deleteActivity(int activityId) {
        return activityRepositoryDao.deleteActivity(activityId);
    }

    public boolean editActivity(int activityId, String name, short maxUsers, byte duration, int activityTypeNumber) {
        return activityRepositoryDao.editActivity(activityId, name, maxUsers, duration, ActivitiesType.getActivity(activityTypeNumber));
    }

    public boolean createActivity(String name, short maxUsers, byte duration, int activityType) {
        return activityRepositoryDao.createActivity(name, maxUsers, duration, ActivitiesType.getActivity(activityType));
    }

    public Object getAllActivitiesByActivityType(ActivitiesType activity) {
        return activityRepositoryDao.getSimplifiedActivatesByActivityType(activity);
    }
}