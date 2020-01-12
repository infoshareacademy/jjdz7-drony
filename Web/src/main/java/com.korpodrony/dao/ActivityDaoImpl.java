package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.ActivityRepositoryDaoInterface;
import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ActivityDaoImpl implements ActivityRepositoryDaoInterface {

    @EJB
    PlanRepositoryDaoInterface planRepositoryDao;

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger("com.korpodrony.dao");

    public boolean createActivity(String name, short maxUsers, byte lengthInQuarters, ActivitiesType activitiesType) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(name);
        activityEntity.setMaxUsers(maxUsers);
        activityEntity.setLengthInQuarters(lengthInQuarters);
        activityEntity.setActivitiesType(activitiesType);
        entityManager.persist(activityEntity);
        logger.info("Created activity " + activityEntity);
        return true;
    }

    @Override
    public int createActivity(ActivityEntity activity) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(activity.getName());
        activityEntity.setMaxUsers(activity.getMaxUsers());
        activityEntity.setLengthInQuarters(activity.getLengthInQuarters());
        activityEntity.setActivitiesType(activity.getActivitiesType());
        entityManager.persist(activityEntity);
        entityManager.flush();
        logger.info("Created activity with id: " + activityEntity.getId() + " from " + activityEntity);
        activityEntity.setAssigned_users(activity.getAssigned_users());
        logger.info("Users which have been assigned: " + activityEntity.getAssigned_users());
        entityManager.merge(activityEntity);
        return activityEntity.getId();
    }

    public boolean assignUsersToActivity(List<Integer> usersIds, int activityID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        logger.debug("activityEntity: " + activityEntity);
        logger.debug("users ids: " + usersIds);
        if (activityEntity != null && usersIds != null) {
            if (activityEntity.getAssigned_users() == null) {
                activityEntity.setAssigned_users(new HashSet<>());
                logger.debug("new HashSet created");
            }
            List<Integer> userIdsListToAssign = getUserIdsListToAssign(usersIds, activityEntity);
            getUsersEntitiesList(userIdsListToAssign).forEach(x -> activityEntity.getAssigned_users()
                    .add(x)
            );
            entityManager.merge(activityEntity);
            logger.debug("usersEntities assigned to Activity");
            return true;
        }
        return false;
    }

    public boolean unassignUsersFromActivity(List<Integer> usersIds, int activityID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        logger.debug("activityEntity: " + activityEntity);
        logger.debug("userIds " + usersIds);
        if (activityEntity != null && usersIds != null) {
            if (activityEntity.getAssigned_users() == null) {
                logger.debug("no assigned users to activity");
                return false;
            }
            activityEntity.setAssigned_users(activityEntity.getAssigned_users()
                    .stream()
                    .filter(x -> !usersIds.contains(x.getId()))
                    .collect(Collectors.toSet())
            );
            entityManager.merge(activityEntity);
            logger.debug("usersEntities unassigned from Activity");
            return true;
        }
        return false;
    }

    public boolean deleteActivity(int activityID) {
        if (hasActivityWithThisID(activityID)) {
            entityManager.createQuery(
                    "select p from Plan p join p.assignedActivities a where a.id=:id", PlanEntity.class)
                    .setParameter("id", activityID)
                    .getResultList().forEach(x -> planRepositoryDao.unassignActivityFromPlan(activityID, x.getId()));
            entityManager.flush();
            entityManager.createQuery("DELETE FROM Activity a WHERE a.id=:id")
                    .setParameter("id", activityID)
                    .executeUpdate();
            entityManager.flush();
            logger.info("Activity with id: " + activityID + "has been removed");
            return true;
        } else {
            logger.info("Activity with id: " + activityID + "doesn't exist");
            return false;
        }
    }

    public boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        if (activityEntity == null) {
            logger.debug("Activity with id: " + activityID + "doesn't exist");
            return false;
        }
        if (checkIfMaxUsersIsSmallerThanAssignedUsersNumber(maxUsers, activityEntity)) {
            logger.debug("Activity with id: " + activityID + "has more users: " + activityEntity.getAssigned_users().size() + "  than max users: " + maxUsers);
            return false;
        }
        logger.debug("Activity before changes: " + activityEntity);
        logger.debug("Values of fileds which will be changed: " + "name: "
                + name + ", maxUsers: " + maxUsers + "lenghtInQuarters: " + lenghtInQuarters + ", activityType: " + activitiesType);
        activityEntity.setName(name);
        activityEntity.setMaxUsers(maxUsers);
        activityEntity.setLengthInQuarters(lenghtInQuarters);
        activityEntity.setActivitiesType(activitiesType);
        entityManager.merge(activityEntity);
        logger.debug("Activity after changes: " + activityEntity);
        return true;
    }

    public boolean hasActivityWithThisID(int activityID) {
        try {
            Object id = entityManager
                    .createQuery("SELECT a.id FROM Activity a where a.id=:id")
                    .setParameter("id", activityID)
                    .getSingleResult();
            logger.debug("Has activity with id: " + activityID);
            return true;
        } catch (NoResultException e) {
            logger.debug("Doesn't have activity with id: " + activityID);
            return false;
        }
    }

    public List<SimplifiedActivityDTO> getAllSimplifiedActivates() {
        try {
            logger.debug("Getting simplified list of activities");
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType) FROM Activity a", SimplifiedActivityDTO.class)
                    .getResultList();
        } catch (NoResultException e) {
            logger.debug("No activities to make simplified list");
            return new ArrayList<>();
        }
    }

    public List<SimplifiedActivityDTO> getAllSimplifiedActivates(ActivitiesType activitiesType) {
        try {
            logger.debug("Getting simplified list of activities which are type of: " + activitiesType);
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType) FROM Activity a WHERE " +
                            "a.activitiesType=:activityType", SimplifiedActivityDTO.class)
                    .setParameter("activityType", activitiesType)
                    .getResultList();
        } catch (NoResultException e) {
            logger.info("No activities which are type of: " + activitiesType + " to make simplified list");
            return new ArrayList<>();
        }
    }

    public ActivityEntity getActivityEntity(int activityId) {
        logger.debug("Getting activityEntity with id: " + activityId);
        return entityManager.find(ActivityEntity.class, activityId);
    }

    public ActivityDTO getActivityDTO(int activityId) {
        try {
            logger.debug("Getting activityDTO with id: " + activityId);
            return getActivityEntity(activityId)
                    .createActivityDTO();
        } catch (NoResultException e) {
            logger.debug("No activity with id: " + activityId);
            return null;
        }
    }

    public List<UserDTO> getAvailableUsersDTO(int activityId) {
        logger.debug("Getting availableUsersDTOs for Activity with id: " + activityId);
        return entityManager.createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname, u.email)" +
                " from User u WHERE u NOT IN (select u from Activity a join a.assigned_users u where a.id=:id)", UserDTO.class)
                .setParameter("id", activityId)
                .getResultList();
    }

    public List<SimplifiedActivityDTO> getAllSimplifiedActivates(String name) {
        try {
            logger.debug("Getting simplified activities by name: " + name);
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType) FROM Activity a WHERE " +
                            "lower(a.name) like :name", SimplifiedActivityDTO.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } catch (NoResultException e) {
            logger.debug("No activities with name: " + name);
            return new ArrayList<>();
        }
    }

    private List<Integer> getUserIdsListToAssign(List<Integer> usersIds, ActivityEntity activityEntity) {
        int numberOfPlaces = activityEntity.getMaxUsers() - activityEntity.getAssigned_users().size();
        if (numberOfPlaces < usersIds.size()) {
            return usersIds.subList(0, numberOfPlaces);
        }
        return usersIds;
    }

    private List<UserEntity> getUsersEntitiesList(List<Integer> usersIds) {
        return entityManager.createQuery("SELECT u from User u WHERE u.id in (:usersIds)", UserEntity.class)
                .setParameter("usersIds", usersIds)
                .getResultList();
    }

    private boolean checkIfMaxUsersIsSmallerThanAssignedUsersNumber(short maxUsers, ActivityEntity activityEntity) {
        return activityEntity.getAssigned_users() != null && activityEntity.getAssigned_users().size() > maxUsers;
    }
}