package com.korpodrony.services;

import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class PlansWebService {

    @EJB
    PlanRepositoryDaoInterface planRepositoryDao;

    Logger logger = LoggerFactory.getLogger("com.korpodrony.services");

    public List<SimplifiedPlanDTO> getAllPlans() {
        logger.debug("getAllSimplifiedPlansDTO called");
        return planRepositoryDao.getAllSimplifiedPlansDTO();
    }

    public boolean hasPlan(int planId) {
        logger.debug("hasPlanWithThisID called");
        return planRepositoryDao.hasPlanWithThisID(planId);
    }

    public PlanDTO getPlanDTO(int id) {
        logger.debug("getPlanDTO called");
        return planRepositoryDao.getPlanDTO(id);
    }

    public boolean deletePlan(int planId) {
        logger.debug("deletePlan called");
        return planRepositoryDao.deletePlan(planId);
    }

    public List<SimplifiedActivityDTO> getAvailableActivities(int planId) {
        logger.debug("getAvailableActivities called");
        return planRepositoryDao.getAvailableSimplifiedActivitiesDTO(planId);
    }

    public boolean assignActivityToPlan(int activityId, int planId) {
        logger.debug("assignActivityToPlan called");
        return planRepositoryDao.assignActivityToPlan(activityId, planId);
    }

    public boolean unassignActivityFromPlan(int activityId, int planId) {
        logger.debug("unassignActivityFromPlan called");
        return planRepositoryDao.unassignActivityFromPlan(activityId, planId);
    }

    public boolean editPlan(int planId, String name) {
        logger.debug("editPlan called");
        return planRepositoryDao.editPlan(planId, name);
    }

    public int createPlan(String name) {
        logger.debug("createPlan called");
        return planRepositoryDao.createPlan(name);
    }
}