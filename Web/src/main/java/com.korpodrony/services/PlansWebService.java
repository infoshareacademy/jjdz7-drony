package com.korpodrony.services;

import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.daoInterfaces.ReportsStatisticsDaoInterface;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
import com.korpodrony.entity.Action;
import com.korpodrony.entity.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class PlansWebService {

    @EJB
    PlanRepositoryDaoInterface planRepositoryDao;

    @Inject
    ReportsStatisticsDaoInterface reportsStatisticsDaoInterface;

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
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.PLANS, Action.DELETE);
        return planRepositoryDao.deletePlan(planId);
    }

    public List<SimplifiedActivityDTO> getAvailableActivities(int planId) {
        logger.debug("getAvailableActivities called");
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.PLANS, Action.GET_AVAILABLE_LIST);
        return planRepositoryDao.getAvailableSimplifiedActivitiesDTO(planId);
    }

    public boolean assignActivitiesToPlan(List<Integer> activitiesIds, int planId) {
        logger.debug("assignActivityToPlan called");
        return planRepositoryDao.assignActivitiesToPlan(activitiesIds, planId);
    }

    public boolean unassignActivitiesFromPlan(List<Integer> activitiesIds, int planId) {
        logger.debug("unassignActivityFromPlan called");
        return planRepositoryDao.unassignActivityFromPlan(activitiesIds, planId);
    }

    public boolean editPlan(int planId, String name) {
        logger.debug("editPlan called");
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.PLANS, Action.EDIT);
        return planRepositoryDao.editPlan(planId, name);
    }

    public int createPlan(String name) {
        logger.debug("createPlan called");
        reportsStatisticsDaoInterface.createReportsStatisticsEntry(View.PLANS, Action.ADD);
        return planRepositoryDao.createPlan(name);
    }
}