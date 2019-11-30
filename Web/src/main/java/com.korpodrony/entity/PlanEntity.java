package com.korpodrony.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "Plan")
@Table(name = "plans")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plan_id;

    private String name;

    @OneToMany
    @JoinTable(
            name = "plans_activities",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<ActivityEntity> assignedActivities;

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ActivityEntity> getAssignedActivities() {
        return assignedActivities;
    }

    public void setAssignedActivities(Set<ActivityEntity> assignedActivities) {
        this.assignedActivities = assignedActivities;
    }
}
