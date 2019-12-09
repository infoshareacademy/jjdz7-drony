package com.korpodrony.dao;

import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ActivityDaoImpl implements ActivityRepositoryDao {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    public boolean createActivity(String name, short maxUsers, byte lengthInQuarters, ActivitiesType activitiesType) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(name);
        activityEntity.setMaxUsers(maxUsers);
        activityEntity.setLengthInQuarters(lengthInQuarters);
        activityEntity.setActivitiesType(activitiesType);
        entityManager.persist(activityEntity);
        return true;
    }

    public boolean assignUserToActivity(int userID, int activityID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        UserEntity userEntity = getUserEntity(userID);
        if (activityEntity != null && userEntity !=null){
            activityEntity
                    .getAssigned_users()
                    .add(userEntity);
            entityManager.merge(activityEntity);
            return true;
        }
        return false;
    }

    public boolean unassignUserFromActivity(int userID, int activityID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        UserEntity userEntity = getUserEntity(userID);
        if (activityEntity != null && userEntity !=null){
            activityEntity
                    .getAssigned_users()
                    .remove(userEntity);
            entityManager.merge(activityEntity);
            return true;
        }
        return false;
    }

    public boolean deleteActivity(int activityID) {
        if (hasActivityWithThisID(activityID)) {
            entityManager.createQuery("DELETE FROM Activity a WHERE a.id=:id")
                    .setParameter("id", activityID)
                    .executeUpdate();
            entityManager.flush();
            entityManager.clear();
            return true;
        }else {
            return false;
        }
    }

    public Activity getActivity(int activityID) {
        return null;
    }

    public boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        return false;
    }

    public List<Activity> getAllActivities() {
        return null;
    }

    public List<Activity> getAllSimplifiedActivities() {
        return null;
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

    private ActivityEntity getActivityEntity(int activityId) {
        return entityManager.find(ActivityEntity.class, activityId);
    }

    private UserEntity getUserEntity(int userId) {
        return entityManager.find(UserEntity.class, userId);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
