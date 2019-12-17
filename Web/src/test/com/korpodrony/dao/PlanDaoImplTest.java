package com.korpodrony.dao;

import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PlanDaoImplTest {
    private EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("test");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private EntityTransaction transaction;
    private PlanDaoImpl testObj = new PlanDaoImpl();

    @BeforeEach
    void clearDB() {
        entityManager = entityManagerFactory.createEntityManager();
        testObj.setEntityManager(entityManager);
        transaction = entityManager.getTransaction();
    }

    @Test
    void createPlanTest() {
        // given
        String name = "Java";
        PlanEntity expectedObj = new PlanEntity();
        expectedObj.setName(name);

        // when
        transaction.begin();
        testObj.createPlan(name);
        transaction.commit();

        // then
        assertThat(entityManager.find(UserEntity.class, 1)).isEqualTo(expectedObj);
    }

    @Test
    void assignActivityToPlanTest() {
        // given
        int planId = 1;
        int activityId = 1;
        transaction.begin();
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("J");
        entityManager.persist(activityEntity);
        entityManager.persist(planEntity);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.assignActivityToPlan(activityId, planId);
        transaction.commit();
        PlanEntity resultPlanEntity = entityManager.find(PlanEntity.class, planId);
        ActivityEntity resultActivityEntity = entityManager.find(ActivityEntity.class, activityId);

        // then
        assertThat(result).isTrue();
        assertThat(resultPlanEntity.getAssignedActivities()).contains(resultActivityEntity);
    }

    @Test
    void unassignActivityFromPlanTest() {
        // given
        // given
        int planId = 1;
        int activityId = 1;
        transaction.begin();
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("J");
        entityManager.persist(activityEntity);
        entityManager.persist(planEntity);
        transaction.commit();
        transaction.begin();
        ActivityEntity activityEntityFromDB = entityManager.find(ActivityEntity.class, activityId);
        PlanEntity planEntityFromDB = entityManager.find(PlanEntity.class, planId);
        planEntityFromDB.getAssignedActivities().add(activityEntityFromDB);
        entityManager.merge(planEntityFromDB);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.unassignActivityFromPlan(activityId, planId);
        transaction.commit();
        PlanEntity resultPlanEntity = entityManager.find(PlanEntity.class, activityId);

        // then
        assertThat(result).isTrue();
        assertThat(resultPlanEntity.getAssignedActivities()).isEmpty();
    }

    @Test
    void deletePlanTest() {
        // given
        int planId = 1;
        transaction.begin();
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("A");
        entityManager.persist(planEntity);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.deletePlan(planId);
        transaction.commit();

        // then
        assertThat(result).isTrue();
        assertThat(entityManager.find(PlanEntity.class, planId)).isNull();
    }

    @Test
    void getPlanTest() {
        // given
        int planId = 1;
        Plan expectedPlan = new Plan("a");
        transaction.begin();
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName(expectedPlan.getName());
        entityManager.persist(planEntity);
        transaction.commit();

        // when
        Plan result = testObj.getPlan(planId);

        // then
        assertThat(result).isEqualTo(expectedPlan);
    }

    @Test
    void editPlanTest() {
        // given
        int planId = 1;
        transaction.begin();
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("A");
        entityManager.persist(planEntity);
        transaction.commit();
        String expectedName = "B";
        // when
        boolean result = testObj.editPlan(1, expectedName);
        PlanEntity resultPlanEntity = entityManager.find(PlanEntity.class, 1);

        // then
        assertThat(result).isTrue();
        assertThat(resultPlanEntity.getName()).isEqualTo(expectedName);
    }

    @Test
    void getAllPlansTest() {
        // given
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("A");
        PlanEntity planEntity2 = new PlanEntity();
        planEntity.setName("A");
        transaction.begin();
        entityManager.persist(planEntity);
        entityManager.persist(planEntity2);
        transaction.commit();
        int expectedLength = 2;

        // when
        List<Plan> result = testObj.getAllPlans();

        // then
        assertThat(result.size()).isEqualTo(expectedLength);
        assertThat(result.get(0).getActivitiesID()).isEmpty();
    }

    @Test
    void getAllSimplifiedPlansTest() {
        // given
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("A");
        PlanEntity planEntity2 = new PlanEntity();
        planEntity.setName("A");
        transaction.begin();
        entityManager.persist(planEntity);
        entityManager.persist(planEntity2);
        transaction.commit();
        int expectedLength = 2;

        // when
        List<Plan> result = testObj.getAllPlans();

        // then
        assertThat(result.size()).isEqualTo(expectedLength);
        assertThat(result.get(0).getName()).isEqualTo(planEntity.getName());
    }

    @Test
    void hasPlanWithThisIDTest() {
        // given
        int planId = 1;
        transaction.begin();
        PlanEntity planEntity = new PlanEntity();
        planEntity.setName("A");
        entityManager.persist(planEntity);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.hasPlanWithThisID(planId);
        transaction.commit();

        // then
        assertThat(result).isTrue();
    }
}