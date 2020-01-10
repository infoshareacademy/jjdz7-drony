package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
import com.korpodrony.entity.PlanEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PlanRepositoryDaoInterface {

    int createPlan(String name);

    int createPlan(PlanEntity planEntity);

    boolean assignActivitiesToPlan(List<Integer> activityIDs, int planID);

    boolean unassignActivityFromPlan(int activityID, int planID);

    boolean unassignActivityFromPlan(List<Integer> activityIDs, int planID);

    boolean deletePlan(int planID);

    boolean editPlan(int planID, String name);

    List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO();

    List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO(String name);

    List<SimplifiedActivityDTO> getAvailableSimplifiedActivitiesDTO(int planId);

    PlanDTO getPlanDTO(int planId);

    boolean hasPlanWithThisID(int planID);
}