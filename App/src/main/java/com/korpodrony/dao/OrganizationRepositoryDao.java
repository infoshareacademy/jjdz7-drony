package com.korpodrony.dao;

import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;

import java.util.List;
import java.util.Set;

public interface OrganizationRepositoryDao {

    boolean createUser(String name, String surname);

    boolean createActivity(String name, short maxUsers, byte duration, ActivitiesType activitiesType);

    boolean createPlan(String name);

    boolean assignUserToActivity(int userID, int activityID);

    boolean unassignUserFromActivity(int userID, int activityID);

    boolean assignActivityToPlan(int activityID, int planID);

    boolean unassignActivityFromPlan(int activityID, int planID);

    boolean deleteUser(int userID);

    boolean deleteActivity(int activityID);

    boolean deletePlan(int planID);

    User getUser(int userID);

    Activity getActivity(int activityID);

    Plan getPlan(int planID);

    boolean editUser(int userID, String name, String surname);

    boolean editActivity(int activityID, String name, short maxUsers, byte lenghtInQuarters, ActivitiesType activitiesType);

    boolean editPlan(int planID, String name);

    List<Integer> getAllUsersIDs();

    List<Integer> getAllActivitiesIDs();

    List<Integer> getAllPlansIDs();

    List<User> getAllUsers();

    List<Activity> getAllActivities();

    List<Plan> getAllPlans();

    boolean hasUser(User user);

    boolean hasActivity(Activity activity);

    boolean hasPlan(Plan plan);

    boolean hasUserWithThisID(int userID);

    boolean hasActivityWithThisID(int activityID);

    boolean hasPlanWithThisID(int planID);

    Set<User> getUsersSet();

    Set<Activity> getActivitiesSet();

    Set<Plan> getPlansSet();
}
