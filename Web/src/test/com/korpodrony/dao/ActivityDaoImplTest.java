package com.korpodrony.dao;

import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.model.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityDaoImplTest {
    private EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("test");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private EntityTransaction transaction;
    private ActivityDaoImpl testObj = new ActivityDaoImpl();

    @BeforeEach
    void clearDB() {
        entityManager = entityManagerFactory.createEntityManager();
        testObj.setEntityManager(entityManager);
        transaction = entityManager.getTransaction();
    }

    @Test
    void createActivityTest() {
        // given
        int id = 1;
        String name = "Jan";
        short maxUsers = 30;
        byte lengthInQuarters = 4;
        ActivitiesType activitiesType = ActivitiesType.EXERCISE;
        ActivityEntity expectedObj = new ActivityEntity();
        expectedObj.setId(id);
        expectedObj.setName(name);
        expectedObj.setMaxUsers(maxUsers);
        expectedObj.setLengthInQuarters(lengthInQuarters);
        expectedObj.setActivitiesType(activitiesType);

        // when
        transaction.begin();
        testObj.createActivity(name, maxUsers, lengthInQuarters, activitiesType);
        transaction.commit();

        // then
        assertThat(entityManager.find(ActivityEntity.class, 1)).isEqualTo(expectedObj);
    }

    @Test
    void assignUserToActivityTest() {
        // given
        int userId = 1;
        int activityId = 1;
        transaction.begin();
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        UserEntity userEntity = new UserEntity();
        userEntity.setName("J");
        userEntity.setSurname("K");
        entityManager.persist(activityEntity);
        entityManager.persist(userEntity);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.assignUserToActivity(userId, activityId);
        transaction.commit();
        UserEntity resultUserEntity = entityManager.find(UserEntity.class, userId);
        ActivityEntity resultActivityEntity = entityManager.find(ActivityEntity.class, activityId);

        // then
        assertThat(result).isTrue();
        assertThat(resultActivityEntity.getAssigned_users()).contains(resultUserEntity);
    }

    @Test
    void unassignUserFromActivity() {
        // given
        int userId = 1;
        int activityId = 1;
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        activityEntity.setAssigned_users(new HashSet<>());
        UserEntity userEntity = new UserEntity();
        userEntity.setName("J");
        userEntity.setSurname("K");
        transaction.begin();
        entityManager.persist(activityEntity);
        entityManager.persist(userEntity);
        transaction.commit();
        transaction.begin();
        ActivityEntity activityEntityFromDB = entityManager.find(ActivityEntity.class, activityId);
        UserEntity userEntityFromDB = entityManager.find(UserEntity.class, userId);
        activityEntityFromDB.getAssigned_users().add(userEntityFromDB);
        entityManager.merge(activityEntityFromDB);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.unassignUserFromActivity(userId, activityId);
        transaction.commit();
        ActivityEntity resultActivityEntity = entityManager.find(ActivityEntity.class, activityId);

        // then
        assertThat(result).isTrue();
        assertThat(activityEntity.getAssigned_users()).isEmpty();
    }

    @Test
    void deleteActivityTest() {
        // given
        transaction.begin();
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        entityManager.persist(activityEntity);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.deleteActivity(1);
        transaction.commit();

        // then
        assertThat(result).isTrue();
        assertThat(entityManager.find(UserEntity.class, 1)).isNull();
    }

    @Test
    void getActivity() {
        // given
        Activity expectedActivity = new Activity("a", (short) 20, (byte) 5, ActivitiesType.EXERCISE);
        transaction.begin();
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(expectedActivity.getName());
        activityEntity.setMaxUsers(expectedActivity.getMaxUsers());
        activityEntity.setLengthInQuarters(expectedActivity.getLengthInQuarters());
        activityEntity.setActivitiesType(expectedActivity.getActivitiesType());
        entityManager.persist(activityEntity);
        transaction.commit();

        // when
        Activity result = testObj.getActivity(1);

        // then
        assertThat(result).isEqualTo(expectedActivity);
    }

    @Test
    void editActivity() {
        // given
        transaction.begin();
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        entityManager.persist(activityEntity);
        transaction.commit();
        String expectedName = "B";
        // when
        boolean result = testObj.editActivity(1, expectedName, (short) 2, (byte) 3, ActivitiesType.WORKSHOP);
        ActivityEntity resultActivityEntity = entityManager.find(ActivityEntity.class, 1);

        // then
        assertThat(result).isTrue();
        assertThat(resultActivityEntity.getName()).isEqualTo(expectedName);
    }

    @Test
    void getAllActivities() {
        // given
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 4);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        ActivityEntity activityEntity2 = new ActivityEntity();
        activityEntity2.setName("B");
        activityEntity2.setMaxUsers((short) 2);
        activityEntity2.setLengthInQuarters((byte) 2);
        activityEntity2.setActivitiesType(ActivitiesType.EXERCISE);
        transaction.begin();
        entityManager.persist(activityEntity);
        entityManager.persist(activityEntity2);
        transaction.commit();
        int expectedLength = 2;

        // when
        List<Activity> result = testObj.getAllActivities();

        // then
        assertThat(result.size()).isEqualTo(expectedLength);
        assertThat(result.get(0).getMaxUsers()).isEqualTo(activityEntity.getMaxUsers());
    }

    @Test
    void getAllSimplifiedActivities() {

        // given
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        ActivityEntity activityEntity2 = new ActivityEntity();
        activityEntity2.setName("B");
        activityEntity2.setMaxUsers((short) 2);
        activityEntity2.setLengthInQuarters((byte) 2);
        activityEntity2.setActivitiesType(ActivitiesType.EXERCISE);
        transaction.begin();
        entityManager.persist(activityEntity);
        entityManager.persist(activityEntity2);
        transaction.commit();
        int expectedLength = 2;

        // when
        List<Activity> result = testObj.getAllSimplifiedActivities();

        // then
        assertThat(result.size()).isEqualTo(expectedLength);
        assertThat(result.get(0).getName()).isEqualTo(activityEntity.getName());
    }

    @Test
    void hasActivityWithThisID() {
        // given
        int activityId = 1;
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName("A");
        activityEntity.setMaxUsers((short) 2);
        activityEntity.setLengthInQuarters((byte) 2);
        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
        transaction.begin();
        entityManager.persist(activityEntity);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.hasActivityWithThisID(activityId);
        transaction.commit();

        // then
        assertThat(result).isTrue();
    }
}