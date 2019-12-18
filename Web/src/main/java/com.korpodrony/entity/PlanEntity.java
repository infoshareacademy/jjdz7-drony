package com.korpodrony.entity;

import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
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

    public SimplifiedPlanDTO createSimplifiedPlanDTO() {
        return new SimplifiedPlanDTO(id, name);
    }

    public void setAssignedActivities(Set<ActivityEntity> assignedActivities) {
        this.assignedActivities = assignedActivities;
    }

    public PlanDTO createPlanDTO() {
        PlanDTO planDTO = new PlanDTO();
        planDTO.setId(id);
        planDTO.setName(name);
        planDTO.setAssignedActivities(assignedActivities.stream()
                .map(ActivityEntity::createSimplifiedActivityDTO)
                .collect(Collectors.toList())
        );
        return planDTO;
    }
}
