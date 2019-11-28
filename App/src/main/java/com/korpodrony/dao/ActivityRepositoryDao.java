package com.korpodrony.dao;

import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

import java.util.List;
import java.util.Set;

public interface ActivityRepositoryDao {

    boolean createActivity(String name, short maxUsers, byte duration, ActivitiesType activitiesType);

    boolean assignUserToActivity(int userID, int activityID);

    boolean unassignUserFromActivity(int userID, int activityID);

    boolean deleteActivity(int activityID);

    Activity getActivity(int activityID);

    boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType);

    List<Integer> getAllActivitiesIDs();


    List<Activity> getAllActivities();


    boolean hasActivity(Activity activity);


    boolean hasActivityWithThisID(int activityID);


    Set<Activity> getActivitiesSet();


}
