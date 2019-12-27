package com.korpodrony;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.utils.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {

        Logger logger = LoggerFactory.getLogger("com.korpodrony");
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        logger.info("Twoja stara");
//        int userId = 1;
//        int activityId = 1;
//        ActivityEntity activityEntity = new ActivityEntity();
//        activityEntity.setName("A");
//        activityEntity.setMaxUsers((short) 2);
//        activityEntity.setLengthInQuarters((byte) 2);
//        activityEntity.setActivitiesType(ActivitiesType.EXERCISE);
//        activityEntity.setAssigned_users(new HashSet<>());
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("J");
//        userEntity.setSurname("K");
//        userEntity.setEmail("twoja@stara.com");
//        UserEntity userEntity2 = new UserEntity();
//        userEntity2.setName("JJ");
//        userEntity2.setSurname("KK");
//        userEntity2.setEmail("twoja2@stara.com");
//        transaction.begin();
//        entityManager.persist(activityEntity);
//        entityManager.persist(userEntity);
//        entityManager.persist(userEntity2);
//        transaction.commit();
//        transaction.begin();
//        ActivityEntity activityEntityFromDB = entityManager.find(ActivityEntity.class, activityId);
////        UserEntity userEntityFromDB = entityManager.find(UserEntity.class, userId);
////        UserEntity userEntityFromDB2 = entityManager.find(UserEntity.class, 2);
////        activityEntityFromDB.getAssigned_users().add(userEntity);
////        activityEntityFromDB.getAssigned_users().add(userEntity2);
//        entityManager.merge(activityEntityFromDB);
//        entityManager.flush();
//        transaction.commit();
//
//        activityEntity.getAssigned_users().add(userEntity);
//        activityEntity.getAssigned_users().add(userEntity2);
//
//        List<ActivityEntity> activityEntities = new ArrayList<>();
//
//        activityEntities.add(activityEntity);

        List result = entityManager.createQuery("SELECT a FROM Activity a", ActivityEntity.class).getResultList();
        List result2 = entityManager.createQuery("SELECT a FROM User a", UserEntity.class).getResultList();
        List result3 = entityManager.createQuery("SELECT a FROM Plan a", PlanEntity.class).getResultList();

        String s = JSONWriter.generateJsonString(result);
        String s2 = JSONWriter.generateJsonString(result2);
        String s3 = JSONWriter.generateJsonString(result3);
//logger.info(s);
        Files.write(Paths.get("/home/patryk/Pulpit/drony2/jjdz7-drony/activities.json"), s.getBytes());
        Files.write(Paths.get("/home/patryk/Pulpit/drony2/jjdz7-drony/users.json"), s2.getBytes());
        Files.write(Paths.get("/home/patryk/Pulpit/drony2/jjdz7-drony/plans.json"), s3.getBytes());
//        ObjectMapper mapper = new ObjectMapper();
//        List<ActivityEntity> list = new ArrayList<>();
//        try {
//            ActivityEntity[] readValue = mapper.readValue(s, ActivityEntity[].class);
//            list = new ArrayList<>(Arrays.asList(readValue));
//        } catch (JsonProcessingException e) {
//            System.out.println("dsads");
//        }
//
//        new ArrayList<String>();
//
//        ActivityEntity activityEntity1 = list.get(0);
//        transaction.begin();
//        entityManager.merge(activityEntity1);
//        transaction.commit();
////        System.out.println(s);
////        System.out.println(s2);
//        System.out.println(list);
    }
}