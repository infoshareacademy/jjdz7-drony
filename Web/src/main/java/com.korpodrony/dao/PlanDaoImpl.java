package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

@Stateless
public class PlanDaoImpl implements PlanRepositoryDaoInterface {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    @Override
    public int createPlan(String name) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName(name);
        entityManager.persist(planEntity);
        return planEntity.getId();
    }

    @Override
    public boolean assignActivityToPlan(int activityID, int planID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        PlanEntity planEntity = getPlanEntity(planID);
        if (activityEntity != null && planEntity != null) {
            if (planEntity.getAssignedActivities() == null) {
                planEntity.setAssignedActivities(new HashSet<>());
            }
            boolean result = planEntity.getAssignedActivities()
                    .add(activityEntity);
            entityManager.merge(activityEntity);
            return result;
        }
        return false;
    }

    @Override
    public boolean unassignActivityFromPlan(int activityID, int planID) {
        ActivityEntity activityEntity = getActivityEntity(activityID);
        PlanEntity planEntity = getPlanEntity(planID);
        if (activityEntity != null && planEntity != null) {
            if (planEntity.getAssignedActivities() == null) {
                planEntity.setAssignedActivities(new HashSet<>());
            }
            boolean result = planEntity.getAssignedActivities()
                    .remove(activityEntity);
            entityManager.merge(planEntity);
            return result;
        }
        return false;
    }

    @Override
    public boolean deletePlan(int planID) {
        if (hasPlanWithThisID(planID)) {
            entityManager.createQuery("DELETE FROM Plan p WHERE p.id=:id")
                    .setParameter("id", planID)
                    .executeUpdate();
            entityManager.flush();
            entityManager.clear();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editPlan(int planId, String name) {
        PlanEntity planEntity = getPlanEntity(planId);
        if (planEntity == null) {
            return false;
        }
        planEntity.setName(name);
        entityManager.merge(planEntity);
        return true;
    }

    public List<SimplifiedPlanDTO> getAllSimplifiedPlansDTO() {
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.SimplifiedPlanDTO(p.id, p.name) " +
                        "FROM Plan p", SimplifiedPlanDTO.class)
                .getResultList();
    }

    @Override
    public List<SimplifiedActivityDTO> getAvailableSimplifiedActivitiesDTO(int planId) {
        return entityManager.createQuery("SELECT new com.korpodrony.dto.SimplifiedActivityDTO(a.id, a.name, a.activitiesType)" +
                " from Activity a WHERE a NOT IN (select a from Plan p join p.assignedActivities a where p.id=:id)", SimplifiedActivityDTO.class)
                .setParameter("id", planId)
                .getResultList();
    }

    @Override
    public PlanDTO getPlanDTO(int planId) {
        return getPlanEntity(planId).createPlanDTO();
    }

    @Override
    public boolean hasPlanWithThisID(int planID) {
        try {
            Object id = entityManager
                    .createQuery("SELECT p.id FROM Plan p where p.id=:id")
                    .setParameter("id", planID)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    private PlanEntity getPlanEntity(int planId) {
        return entityManager.find(PlanEntity.class, planId);
    }

    private ActivityEntity getActivityEntity(int activityId) {
        return entityManager.find(ActivityEntity.class, activityId);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
