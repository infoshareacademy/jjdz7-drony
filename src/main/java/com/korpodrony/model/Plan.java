package com.korpodrony.model;

public class Plan {
    private static int currentID = 0;
    private int planID;
    private String name;
    private int[] activitiesID;

    boolean assignActivity(Activity activity){
        return false;
    }

    boolean unassignActivity(Activity activity){
        return false;
    }
}
