package com.korpodrony.dao;

import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import com.korpodrony.model.Plan;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PlanDaoImpl implements PlanRepositoryDao {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    @Override
    public boolean createPlan(String name) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName(name);
        entityManager.persist(planEntity);
        return true;
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
    public Plan getPlan(int planID) {
        return null;
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

    @Override
    public List<Plan> getAllPlans() {
        return entityManager
                .createQuery("SELECT p FROM Plan p", PlanEntity.class)
                .getResultList()
                .stream()
                .map(PlanEntity::createPlan)
                .collect(Collectors.toList());
    }

    public List<Plan> getAllSimplifiedPlans() {
        try {
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.PlanDTO(p.id, p.name) FROM Plan p", PlanDTO.class)
                    .getResultList()
                    .stream()
                    .map(PlanDTO::createSimplifiedPlan)
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            return null;
        }
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
