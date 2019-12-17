package com.korpodrony.dao;

import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;
import com.korpodrony.model.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface ActivityRepositoryDao {

    boolean createActivity(String name, short maxUsers, byte duration, ActivitiesType activitiesType);

    boolean assignUserToActivity(int userID, int activityID);

    boolean unassignUserFromActivity(int userID, int activityID);

    boolean deleteActivity(int activityID);

    Activity getActivity(int activityID);

    boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType);

    default List<Integer> getAllActivitiesIDs() {
        return null;
    }

    default List<User> getAssignedUsers(int activityId){
        return null;
    }

    List<Activity> getAllActivities();

    default boolean hasActivity(Activity activity) {
        return false;
    }

    boolean hasActivityWithThisID(int activityID);

    default Set<Activity> getActivitiesSet() {
        return null;
    }

    default List<Activity> getSimplifiedActivatesByActivityType(ActivitiesType activity){
        return null;
    }

    default List<Activity> getAllSimplifiedActivities(){
        return null;
    }
}