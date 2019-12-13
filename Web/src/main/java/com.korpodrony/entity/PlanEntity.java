package com.korpodrony.entity;

import com.korpodrony.model.Plan;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Plan")
@Table(name = "plans")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany
    @JoinTable(
            name = "plans_activities",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<ActivityEntity> assignedActivities;

    public int getId() {
        return id;
    }

    public void setId(int plan_id) {
        this.id = plan_id;
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

    public Plan createPlan() {
        Plan plan = new Plan();
        plan.setId(id);
        plan.setName(name);
        if (assignedActivities == null) {
            plan.setActivitiesID(new HashSet<>());
        } else {
            plan.setActivitiesID(assignedActivities.stream()
                    .map(ActivityEntity::getId)
                    .collect(Collectors.toSet()));
        }
        return plan;
    }

    public void setAssignedActivities(Set<ActivityEntity> assignedActivities) {
        this.assignedActivities = assignedActivities;
    }
}
