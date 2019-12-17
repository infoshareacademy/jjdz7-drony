package com.korpodrony;

import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

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
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setName("JJ");
        userEntity2.setSurname("KK");
        transaction.begin();
        entityManager.persist(activityEntity);
        entityManager.persist(userEntity);
        entityManager.persist(userEntity2);
        transaction.commit();
        transaction.begin();
        ActivityEntity activityEntityFromDB = entityManager.find(ActivityEntity.class, activityId);
        UserEntity userEntityFromDB = entityManager.find(UserEntity.class, userId);
        UserEntity userEntityFromDB2 = entityManager.find(UserEntity.class, 2);
        activityEntityFromDB.getAssigned_users().add(userEntityFromDB);
        activityEntityFromDB.getAssigned_users().add(userEntityFromDB2);
        entityManager.merge(activityEntityFromDB);
        transaction.commit();

        List<UserEntity> resultList = entityManager.createQuery("select u from User u join u. users_activities a where a.id = 1", UserEntity.class)
                .getResultList();

        System.out.println(resultList);
    }
}