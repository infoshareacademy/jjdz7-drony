package com.korpodrony.dao;

import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

public class ActivityDaoImpl implements  ActivityRepositoryDao{

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    @Override
    public boolean createActivity(String name, short maxUsers, byte lengthInQuarters, ActivitiesType activitiesType) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(name);
        activityEntity.setMaxUsers(maxUsers);
        activityEntity.setLengthInQuarters(lengthInQuarters);
        activityEntity.setActivitiesType(activitiesType);
        entityManager.persist(activityEntity);
        return true;
    }

    @Override
    public boolean assignUserToActivity(int userID, int activityID) {
        if (hasActivityWithThisID(activityID) && entityManager.find(UserEntity.class, 1) != null){

        }
        return false;
    }

    @Override
    public boolean unassignUserFromActivity(int userID, int activityID) {
        return false;
    }

    @Override
    public boolean deleteActivity(int activityID) {
        return false;
    }

    @Override
    public Activity getActivity(int activityID) {
        return null;
    }

    @Override
    public boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        return false;
    }

    @Override
    public List<Integer> getAllActivitiesIDs() {
        return null;
    }

    @Override
    public List<Activity> getAllActivities() {
        return null;
    }

    @Override
    public boolean hasActivity(Activity activity) {
        return false;
    }

    @Override
    public boolean hasActivityWithThisID(int activityID) {
        try {
            Object id = entityManager
                    .createQuery("SELECT a.id FROM Activity a where a.id=:id")
                    .setParameter("id", activityID)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Set<Activity> getActivitiesSet() {
        return null;
    }

    private ActivityEntity getActivityEntity(int activityId) {
        return entityManager.find(ActivityEntity.class, activityId);
    }

}
