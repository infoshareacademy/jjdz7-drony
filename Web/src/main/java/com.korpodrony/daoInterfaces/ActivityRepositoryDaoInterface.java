package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.model.ActivitiesType;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ActivityRepositoryDaoInterface {

    boolean createActivity(String name, short maxUsers, byte duration, ActivitiesType activitiesType);

    int createActivity(ActivityEntity activity);

    boolean assignUsersToActivity(List<Integer> usersIds, int activityID);

    boolean unassignUsersFromActivity(List<Integer> usersIds, int activityID);

    boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType);

    boolean deleteActivity(int activityID);

    boolean hasActivityWithThisID(int activityID);

    List<SimplifiedActivityDTO> getAllSimplifiedActivates();

    List<SimplifiedActivityDTO> getAllSimplifiedActivates(ActivitiesType activitiesType);

    ActivityEntity getActivityEntity(int activityId);

    ActivityDTO getActivityDTO(int activityId);

    List<UserDTO> getAvailableUsersDTO(int activityId);

    List<SimplifiedActivityDTO> getAllSimplifiedActivates(String name);
}