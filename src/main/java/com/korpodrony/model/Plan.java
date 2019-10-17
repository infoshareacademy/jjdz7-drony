package com.korpodrony.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Plan {
    private static int currentID = 0;
    private int ID;
    private String name;
    private Set<Integer> activitiesID;

    public Plan(int ID, String name, Set<Integer> activitiesID) {
        this.ID = ID;
        this.name = name;
        this.activitiesID = activitiesID;
    }

    public Plan() {
    }
    public static void setCurrentID(Set<Plan> plans){
        int maxValue = 0;
        for (Plan plan: plans){
            if (plan.getID()>maxValue){
                maxValue=plan.getID();
            }
        }
        setCurrentID(maxValue);
    }

    public static int getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(int currentID) {
        Plan.currentID = currentID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getActivitiesID() {
        return activitiesID;
    }

    public void setActivitiesID(Set<Integer> activitiesID) {
        this.activitiesID = activitiesID;
    }

    public Plan(String name) {
        this(++currentID, name, new HashSet<>());
    }

    public void editPlan(String name) {
        setName(name);
    }

    public boolean assignActivity(Activity activity) {
        if (activitiesID.contains(activity.getID())) {
            return false;
        }
        activitiesID.add(activity.getID());
        return true;
    }

    public boolean assignActivity(int index) {
        if (activitiesID.contains(index)) {
            return false;
        }
        activitiesID.add(index);
        return true;
    }

    public boolean unassignActivity(int index) {
        if (!activitiesID.contains(index)) {
            return false;
        }
        activitiesID.remove(index);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return ID == plan.ID;
    }

    @Override
    public String toString() {
        return "Plan"+"ID = " + ID +
                ", nazwa planu:  " + name +
                "ID przypisanych zajęć: " + activitiesID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}


