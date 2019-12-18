package com.korpodrony.services;

import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class PlansWebService {

    @EJB
    PlanRepositoryDaoInterface planRepositoryDao;

    public List<SimplifiedPlanDTO> getAllPlans() {
        return planRepositoryDao.getAllSimplifiedPlansDTO();
    }

    public boolean hasPlan(int planId) {
        return planRepositoryDao.hasPlanWithThisID(planId);
    }

    public PlanDTO getPlanDTO(int id) {
        return planRepositoryDao.getPlanDTO(id);
    }

    public boolean deletePlan(int planId) {
        return planRepositoryDao.deletePlan(planId);
    }

    public List<SimplifiedActivityDTO> getAvailableActivities(int planId) {
        return planRepositoryDao.getAvailableSimplifiedActivitiesDTO(planId);
    }

    public boolean assignActivityToPlan(int activityId, int planId) {
        return planRepositoryDao.assignActivityToPlan(activityId, planId);
    }

    public boolean unassignActivityFromPlan(int activityId, int planId) {
        return planRepositoryDao.unassignActivityFromPlan(activityId, planId);
    }

    public boolean editPlan(int planId, String name) {
        return planRepositoryDao.editPlan(planId, name);
    }

    public int createPlan(String name) {
        return planRepositoryDao.createPlan(name);
    }
}