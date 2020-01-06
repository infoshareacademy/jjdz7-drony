package com.korpodrony.services;

import com.korpodrony.daoInterfaces.ActivityRepositoryDaoInterface;
import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.model.ActivitiesType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class ActivitiesWebService {

    Logger logger = LoggerFactory.getLogger("com.korpodrony.services");

    @EJB
    ActivityRepositoryDaoInterface activityRepositoryDao;

    public boolean hasActivity(int activityId) {
        logger.debug("Has Activity called");
        return activityRepositoryDao.hasActivityWithThisID(activityId);
    }

    public List<SimplifiedActivityDTO> getAllActivities() {
        logger.debug("Getting AllSimplifiedActivities called");
        return activityRepositoryDao.getAllSimplifiedActivates();
    }

    public ActivityDTO getActivityDTO(int activityId) {
        logger.debug("Getting activityDTO called");
        return activityRepositoryDao.getActivityDTO(activityId);
    }

    public boolean assignUserToActivity(int userId, int activityId) {
        logger.debug("Assigning user to activity called");
        return activityRepositoryDao.assignUserToActivity(userId, activityId);
    }

    public boolean unassignUserFromActivity(int userId, int activityId) {
        logger.debug("Unssigning user to activity called");
        return activityRepositoryDao.unassignUserFromActivity(userId, activityId);
    }

    public boolean deleteActivity(int activityId) {
        logger.debug("Deleting user called");
        return activityRepositoryDao.deleteActivity(activityId);
    }

    public boolean editActivity(int activityId, String name, short maxUsers, byte duration, int activityTypeNumber) {
        logger.debug("Editing user called");
        return activityRepositoryDao.editActivity(activityId, name, maxUsers, duration, ActivitiesType.getActivity(activityTypeNumber));
    }

    public boolean createActivity(String name, short maxUsers, byte duration, int activityType) {
        logger.debug("Creating user called");
        return activityRepositoryDao.createActivity(name, maxUsers, duration, ActivitiesType.getActivity(activityType));
    }

    public List<UserDTO> getAvailableUserDTO(int activityId) {
        logger.debug("Getting getAvailableUsersDTO called");
        return activityRepositoryDao.getAvailableUsersDTO(activityId);
    }

    public List<SimplifiedActivityDTO> getAllActivitiesByActivityType(ActivitiesType activity) {
        logger.debug("Getting AllSimplifiedActivities by ActivitiesType called");
        return activityRepositoryDao.getAllSimplifiedActivates(activity);
    }
}