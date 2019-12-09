package com.korpodrony.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.*;

class ActivityDaoImplTest {
    private EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("test");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private EntityTransaction transaction;
    private UserDaoImpl testObj = new UserDaoImpl();

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        testObj.setEntityManager(entityManager);
        transaction = entityManager.getTransaction();
    }

    @Test
    void createActivity() {
        // given

        // when

        // then
    }

    @Test
    void assignUserToActivity() {
        // given

        // when

        // then
    }

    @Test
    void unassignUserFromActivity() {
        // given

        // when

        // then
    }

    @Test
    void deleteActivity() {
        // given

        // when

        // then
    }

    @Test
    void getActivity() {
        // given

        // when

        // then
    }

    @Test
    void editActivity() {
        // given

        // when

        // then
    }

    @Test
    void getAllActivities() {
        // given

        // when

        // then
    }

    @Test
    void hasActivityWithThisID() {
        // given

        // when

        // then
    }
}