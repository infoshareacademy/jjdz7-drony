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
    OrganizationRepositoryDao organizationRepositoryDao;

    @Inject
    ActivitiesWebService activitiesWebService;

    public List<Plan> getAllPlans() {
        return organizationRepositoryDao.getAllPlans()
                .stream()
                .sorted((x, y) -> new PlanIDComparator()
                        .compare(x, y))
                .collect(Collectors.toList());
    }

    public boolean hasPlan(int planId) {
        return organizationRepositoryDao.hasPlanWithThisID(planId);
    }

    public List<Activity> getAssignedActivities(int planId) {
        return getPlan(planId)
                .getActivitiesID()
                .stream()
                .map(x -> organizationRepositoryDao.getActivity(x))
                .sorted(Comparator.comparingInt(Activity::getId))
                .collect(Collectors.toList());
    }

    public Plan getPlan(int id) {
        return organizationRepositoryDao.getPlan(id);
    }

    public boolean deletePlan(int planId) {
        if (organizationRepositoryDao.deletePlan(planId)) {
            setPlanIdToLastValue();
            return true;
        }
        return false;
    }

    public List<Activity> getAvailableActivities(int planId) {
        return activitiesWebService
                .getAllActivities()
                .stream()
                .filter(x -> !getAssignedActivities(planId).contains(x))
                .collect(Collectors.toList());
    }

    public boolean assignActivityToPlan(int activityId, int planId) {
        return organizationRepositoryDao.assignActivityToPlan(activityId, planId);
    }

    public boolean unassignActivityFromPlan(int activityId, int planId) {
        return organizationRepositoryDao.unassignActivityFromPlan(activityId, planId);
    }

    public boolean editPlan(int planId, String name) {
        return organizationRepositoryDao.editPlan(planId, name);
    }

    public boolean createPlan(String name) {
        return organizationRepositoryDao.createPlan(name);
    }

    private void setPlanIdToLastValue() {
        int currentId = organizationRepositoryDao.getAllPlans()
                .stream()
                .map(Plan::getId).max(Comparator.comparingInt(x -> x))
                .orElse(0);
        Plan.setCurrentID(currentId);
    }
}
