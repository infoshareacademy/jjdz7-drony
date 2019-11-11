package com.korpodrony.services;

import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class PlansWebService {

    @EJB
    OrganizationRepositoryDao dao;

    @Inject
    ActivitiesWebService activitiesWebService;

    public List<Plan> getAllPlans() {
        return dao.getAllPlans()
                .stream()
                .sorted((x, y) -> new PlanIDComparator()
                        .compare(x, y))
                .collect(Collectors.toList());
    }

    public boolean hasPlan(int planId) {
        return dao.hasPlanWithThisID(planId);
    }

    public List<Activity> getAssignedActivities(int planId) {
        return getPlan(planId)
                .getActivitiesID()
                .stream()
                .map(x -> dao.getActivity(x))
                .sorted(Comparator.comparingInt(Activity::getId))
                .collect(Collectors.toList());
    }

    public Plan getPlan(int id) {
        return dao.getPlan(id);
    }

    public boolean deleteActivity(int planId) {
        return dao.deletePlan(planId);
    }

    public List<Activity> getAvaiableActivities(int planId) {
        return activitiesWebService
                .getAllActivities()
                .stream()
                .filter(x -> !getAssignedActivities(planId).contains(x))
                .collect(Collectors.toList());
    }

    public boolean assignActivityToPlan(int activityId, int planId) {
        return dao.assignActivityToPlan(activityId, planId);
    }

    public boolean unassignActivityFromPlan(int activityId, int planId) {
        return dao.unassignActivityFromPlan(activityId, planId);
    }

    public boolean editPlan(int planId, String name) {
        return dao.editPlan(planId, name);
    }

    public boolean createPlan(String name) {
        return true;
    }
}
