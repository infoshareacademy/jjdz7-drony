package com.korpodrony.dto;

import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;

public class PlanDTO {

    private int id;
    private String name;

    public PlanDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Plan createSimplifiedPlan() {
        Plan plan = new Plan();
        plan.setId(id);
        plan.setName(name);
        return plan;
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
}
