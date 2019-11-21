package com.korpodrony;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    private static EntityManager entityManager;

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("korpodrony-demo");
        entityManager = entityManagerFactory.createEntityManager();

    }
}