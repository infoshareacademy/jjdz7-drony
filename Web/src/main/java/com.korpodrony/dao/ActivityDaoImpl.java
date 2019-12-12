package com.korpodrony.dao;

import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (activityEntity != null && userEntity != null) {
            if (activityEntity.getAssigned_users() == null) {
                activityEntity.setAssigned_users(new HashSet<>());
            }
            boolean result = activityEntity.getAssigned_users()
                    .add(userEntity);
            entityManager.merge(activityEntity);
            return result;
        }
        return false;
    }

    public boolean unassignUserFromActivity(int userID, int activityID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        UserEntity userEntity = getUserEntity(userID);
        if (activityEntity != null && userEntity != null) {
            if (activityEntity.getAssigned_users() == null) {
                activityEntity.setAssigned_users(new HashSet<>());
            }
            boolean result = activityEntity.getAssigned_users()
                    .remove(userEntity);
            entityManager.merge(activityEntity);
            return result;
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
        } else {
            return false;
        }
    }

    public Activity getActivity(int activityID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        return activityEntity != null ? activityEntity.createActivity() : null;
    }

    public boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        if (hasActivityWithThisID(activityID)) {
            ActivityEntity activityEntity = getActivityEntity(activityID);
            activityEntity.setName(name);
            activityEntity.setMaxUsers(maxUsers);
            activityEntity.setLengthInQuarters(lenghtInQuarters);
            activityEntity.setActivitiesType(activitiesType);
            entityManager.merge(activityEntity);
            return true;
        }
        return false;
    }

    public List<Activity> getAllActivities() {
        return entityManager
                .createQuery("SELECT a FROM Activity a", ActivityEntity.class)
                .getResultList()
                .stream()
                .map(ActivityEntity::createActivity)
                .collect(Collectors.toList());
    }

    public List<Activity> getAllSimplifiedActivities() {
        try {
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.ActivityDTO(a.id, a.name) FROM Activity a", ActivityDTO.class)
                    .getResultList()
                    .stream()
                    .map(ActivityDTO::createSimplifiedActivity)
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            return null;
        }
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
