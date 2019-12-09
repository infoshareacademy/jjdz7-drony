package com.korpodrony;

import com.korpodrony.dto.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class app {
    private static EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//        entityManager.persist(new UserEntity("adam", "madam"));
//        entityManager.persist(new UserEntity("ad", "madam"));
//        entityManager.persist(new UserEntity("ada", "madam"));
//        entityManager.persist(new UserEntity("madam", "madam"));
//        transaction.commit();
//
//        transaction.begin();
//        entityManager
//                .createQuery("DELETE FROM User ")
//                .executeUpdate();
//        entityManager.flush();
//        entityManager.clear();
//        transaction.commit();

        UserDTO singleResult = entityManager
                .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname) FROM User u"
                        , UserDTO.class)
                .getSingleResult();

        System.out.println(singleResult);
    }
}