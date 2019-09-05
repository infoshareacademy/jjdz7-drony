package com.korpodrony.model;

import com.korpodrony.exceptions.ElementIsNotUniqueException;
import com.korpodrony.exceptions.EmptyArrayException;
import com.korpodrony.exceptions.NoElementFoundException;
import com.korpodrony.utils.ArrayService;

public class Plan {
    private static int currentID = 0;
    private int planID;
    private String name;
    private Integer[] activitiesID;

    public Plan(int planID, String name, Integer[] activitiesID) {
        this.planID = planID;
        this.name = name;
        this.activitiesID = activitiesID;
    }

    public Plan(String name) {
        this(++currentID, name, new Integer[0]);
    }

    public void editPlan(String name) {
        setName(name);
    }

    public boolean assignActivity(Activity activity) {
        try {
            activitiesID = ArrayService.addToArray(activitiesID, activity.getActivityID());
            return true;
        } catch (ElementIsNotUniqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean unassignActivity(Activity activity) {
        try {
            activitiesID = ArrayService.removeElement(activitiesID, activity.getActivityID());
            return true;
        } catch (NoElementFoundException | EmptyArrayException noElementFoundException) {
            System.out.println(noElementFoundException.getMessage());
            return false;
        }
    }

    private void setName(String name) {
        this.name = name;
    }
}
