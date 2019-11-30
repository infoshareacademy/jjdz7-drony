package com.korpodrony;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class app {
    private static EntityManager entityManager;
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("korpodrony-hibernate");
        entityManager = entityManagerFactory.createEntityManager();
    }
}