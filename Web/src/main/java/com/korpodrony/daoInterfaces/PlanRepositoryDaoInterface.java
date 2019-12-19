package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PlanRepositoryDaoInterface {

    int createPlan(String name);

    boolean assignActivityToPlan(int activityID, int planID);

    boolean unassignActivityFromPlan(int activityID, int planID);

    boolean deletePlan(int planID);

    boolean editPlan(int planID, String name);

    List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO();

    List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO(String name);

    List<SimplifiedActivityDTO> getAvailableSimplifiedActivitiesDTO(int planId);

    PlanDTO getPlanDTO(int planId);

    boolean hasPlanWithThisID(int planID);
}