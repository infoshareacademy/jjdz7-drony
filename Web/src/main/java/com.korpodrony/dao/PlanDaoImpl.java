package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PlanDaoImpl implements PlanRepositoryDaoInterface {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger("com.korpodrony.dao");

    @Override
    public int createPlan(String name) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName(name);
        entityManager.persist(planEntity);
        logger.info("created plan: " + planEntity + " from name: " + name);
        return planEntity.getId();
    }

    @Override
    public int createPlan(PlanEntity planEntity) {
        PlanEntity plan = new PlanEntity();
        plan.setName(planEntity.getName());
        entityManager.persist(plan);
        logger.info("Created plan: " + plan + "from planEntity: " + planEntity);
        return plan.getId();
    }

    @Override
    public boolean assignActivitiesToPlan(List<Integer> activityIDs, int planID) {
        PlanEntity planEntity = getPlanEntity(planID);
        logger.debug("PlanEntity: " + planEntity + ", activtiesIds: " + activityIDs);
        if (planEntity != null && activityIDs != null) {
            if (planEntity.getAssignedActivities() == null) {
                planEntity.setAssignedActivities(new HashSet<>());
                logger.debug("New HashSet created");
            }
            getActivitiesEntitiesList(activityIDs).forEach(
                    x -> planEntity.getAssignedActivities()
                            .add(x)
            );
            entityManager.merge(planEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean unassignActivityFromPlan(int activityID, int planID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        logger.debug("ActivityEntity: " + activityEntity);
        PlanEntity planEntity = getPlanEntity(planID);
        logger.debug("PlanEntity: " + planEntity);
        if (activityEntity != null && planEntity != null) {
            if (planEntity.getAssignedActivities() == null) {
                return false;
            }
            boolean result = planEntity.getAssignedActivities()
                    .remove(activityEntity);
            entityManager.merge(planEntity);
            logger.debug("Activity unassigned from Plan: " + result);
            return result;
        }
        return false;
    }

    @Override
    public boolean unassignActivityFromPlan(List<Integer> activityIDs, int planID) {
        PlanEntity planEntity = getPlanEntity(planID);
        logger.debug("PlanEntity: " + planEntity + ", activtiesIds: " + activityIDs);
        if (planEntity != null) {
            if (checkIfAssignedActiviesOrIdsToAssignAreNotNull(activityIDs, planEntity)) {
                return false;
            }
            planEntity.setAssignedActivities(planEntity.getAssignedActivities()
                    .stream()
                    .filter(x -> !activityIDs.contains(x.getId()))
                    .collect(Collectors.toSet())
            );
            entityManager.merge(planEntity);
            return true;
        }
        return false;
    }

    private boolean checkIfAssignedActiviesOrIdsToAssignAreNotNull(List<Integer> activityIDs, PlanEntity planEntity) {
        return planEntity.getAssignedActivities() == null || activityIDs == null;
    }

    @Override
    public boolean deletePlan(int planID) {
        if (hasPlanWithThisID(planID)) {
            entityManager.createQuery("DELETE FROM Plan p WHERE p.id=:id")
                    .setParameter("id", planID)
                    .executeUpdate();
            entityManager.flush();
            entityManager.clear();
            logger.info("Plan with id: " + planID + "has been removed");
            return true;
        } else {
            logger.info("Plan with id: " + planID + "doesn't exist");
            return false;
        }
    }

    @Override
    public boolean editPlan(int planId, String name) {
        PlanEntity planEntity = getPlanEntity(planId);
        logger.debug("Plan before changes: " + planEntity);
        logger.debug("Values of fileds which will be changed: " + "name: " + name);
        if (planEntity == null) {
            return false;
        }
        planEntity.setName(name);
        entityManager.merge(planEntity);
        logger.debug("Plan after changes: " + planEntity);
        return true;
    }

    public List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO() {
        logger.debug("Getting simplified list of plans");
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.SimplifiedPlanDTO(p.id, p.name) " +
                        "FROM Plan p", SimplifiedPlanDTO.class)
                .getResultList();
    }

    public List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO(String name) {
        logger.debug("Getting simplified plans by name: " + name);
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.SimplifiedPlanDTO(p.id, p.name) " +
                        "FROM Plan p WHERE lower(p.name) like :name", SimplifiedPlanDTO.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<SimplifiedActivityDTO> getAvailableSimplifiedActivitiesDTO(int planId) {
        logger.debug("Getting available simplified ActivitiesDTOs for plan with id: " + planId);
        return entityManager.createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType)" +
                " from Activity a WHERE a NOT IN (select a from Plan p join p.assignedActivities a where p.id=:id)", SimplifiedActivityDTO.class)
                .setParameter("id", planId)
                .getResultList();
    }

    @Override
    public PlanDTO getPlanDTO(int planId) {
        logger.debug("Getting plan dto for id: " + planId);
        return getPlanEntity(planId).createPlanDTO();
    }

    @Override
    public boolean hasPlanWithThisID(int planID) {
        try {
            Object id = entityManager
                    .createQuery("SELECT p.id FROM Plan p where p.id=:id")
                    .setParameter("id", planID)
                    .getSingleResult();
            logger.debug("Has plan with id: " + planID);
            return true;
        } catch (NoResultException e) {
            logger.info("Doesn't have plan with id: " + planID);
            return false;
        }
    }

    private PlanEntity getPlanEntity(int planId) {
        logger.debug("Getting planEntity for id: " + planId);
        return entityManager.find(PlanEntity.class, planId);
    }

    private List<ActivityEntity> getActivitiesEntitiesList(List<Integer> activitiesIds) {
        return entityManager.createQuery("SELECT a from Activity a WHERE a.id in (:activtiesIds)", ActivityEntity.class)
                .setParameter("activtiesIds", activitiesIds)
                .getResultList();
    }

    private ActivityEntity getActivityEntity(int activityId) {
        logger.debug("Getting activityEntity for id: " + activityId);
        return entityManager.find(ActivityEntity.class, activityId);
    }
}
