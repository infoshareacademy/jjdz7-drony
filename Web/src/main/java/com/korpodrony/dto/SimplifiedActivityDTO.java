package com.korpodrony.dto;

import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;

public class SimplifiedActivityDTO {
    int id;
    String name;
    private ActivitiesType activitiesType;

    public SimplifiedActivityDTO(int id, String name, ActivitiesType activitiesType) {
        this.id = id;
        this.name = name;
        this.activitiesType = activitiesType;
    }

    public Activity createSimplifiedActivity() {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setActivitiesType(activitiesType);
        return activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivitiesType getActivitiesType() {
        return activitiesType;
    }

    public void setActivitiesType(ActivitiesType activitiesType) {
        this.activitiesType = activitiesType;
    }
}
