package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.ActivityRepositoryDaoInterface;
import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Stateless
public class ActivityDaoImpl implements ActivityRepositoryDaoInterface {

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

    public boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        if (activityEntity == null) {
            return false;
        }
        activityEntity.setName(name);
        activityEntity.setMaxUsers(maxUsers);
        activityEntity.setLengthInQuarters(lenghtInQuarters);
        activityEntity.setActivitiesType(activitiesType);
        entityManager.merge(activityEntity);
        return true;
    }

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

    public List<SimplifiedActivityDTO> getAllSimplifiedActivates() {
        try {
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType) FROM Activity a", SimplifiedActivityDTO.class)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<SimplifiedActivityDTO> getAllSimplifiedActivates(ActivitiesType activitiesType) {
        try {
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType) FROM Activity a WHERE " +
                            "a.activitiesType=:activityType", SimplifiedActivityDTO.class)
                    .setParameter("activityType", activitiesType)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public ActivityEntity getActivityEntity(int activityId) {
        return entityManager.find(ActivityEntity.class, activityId);
    }

    public ActivityDTO getActivityDTO(int activityId) {
        try {
            return getActivityEntity(activityId)
                    .createActivityDTO();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserDTO> getAvailableUsersDTO(int activityId) {
        return entityManager.createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname)" +
                " from User u WHERE u NOT IN (select u from User u join u.users_activities a where a.id=:id)", UserDTO.class)
                .setParameter("id", activityId)
                .getResultList();
    }

    public List<SimplifiedActivityDTO> getAllSimplifiedActivates(String name) {
        try {
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType) FROM Activity a WHERE " +
                            "lower(a.name) like :name", SimplifiedActivityDTO.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private UserEntity getUserEntity(int userId) {
        return entityManager.find(UserEntity.class, userId);
    }
}