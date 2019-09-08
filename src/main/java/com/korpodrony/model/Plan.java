package com.korpodrony.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Plan {
    private static int currentID = 0;
    private int planID;
    private String name;
    private Set<Integer> activitiesID;

    public Plan(int planID, String name, Set<Integer> activitiesID) {
        this.planID = planID;
        this.name = name;
        this.activitiesID = activitiesID;
    }

    public Plan(String name) {
        this(++currentID, name, new HashSet<>());
    }

    public void editPlan(String name) {
        setName(name);
    }

    public boolean assignActivity(Activity activity) {
        if (activitiesID.contains(activity.getActivityID())) {
            return false;
        }
        activitiesID.add(activity.getActivityID());
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

    public int getPlanID() {
        return planID;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return planID == plan.planID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(planID);
    }
}
