package com.korpodrony.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korpodrony.daoInterfaces.ActivityRepositoryDaoInterface;
import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import com.korpodrony.entity.UserEntity;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class UploadService {

    @EJB
    UserRepositoryDaoInterface userRepositoryDao;

    @EJB
    ActivityRepositoryDaoInterface activityRepositoryDao;

    @EJB
    PlanRepositoryDaoInterface planRepositoryDao;

    public void uploadFile(InputStream fileContent) throws IOException {
        String fileContentString = extractFileContentString(fileContent);

        if (uploadUsers(fileContentString)) {
            return;
        } else if (uploadActivities(fileContentString)) {
            return;
        } else if (uploadPlans(fileContentString)) {
            return;
        } else {
            throw new IOException("Niepoprawny plik");
        }
    }

    private String extractFileContentString(InputStream fileContent) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (fileContent, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }

    private boolean uploadPlans(String json) {
        Set<PlanEntity> planEntitySet = parsePlanEntityFromJSON(json);
        if (planEntitySet.isEmpty()) {
            return false;
        }
        updatePlans(planEntitySet);
        return true;
    }

    private boolean uploadActivities(String json) {
        Set<ActivityEntity> activityEntitySet = parseActivityEntityFromJSON(json);
        if (activityEntitySet.isEmpty()) {
            return false;
        }
        updateActivities(activityEntitySet);
        return true;
    }

    private boolean uploadUsers(String json) {
        Set<UserEntity> userEntitySet = parseUserEntityFromJSON(json);
        if (userEntitySet.isEmpty()) {
            return false;
        }
        updateUsers(userEntitySet);
        return true;
    }

    private void updatePlans(Set<PlanEntity> planEntities) {
        preparePlansActivities(planEntities);
        planEntities.forEach(plan -> {
            plan.setId(planRepositoryDao.createPlan(plan));
            plan.getAssignedActivities().forEach(
                    activity -> planRepositoryDao.assignActivityToPlan(activity.getId(), plan.getId())
            );
        });
    }

    private void preparePlansActivities(Set<PlanEntity> planEntities) {
        Map<Integer, Integer> activitiesIds = new HashMap<>();
        planEntities.forEach(x -> x.getAssignedActivities()
                .forEach(y -> {
                            Integer id = activitiesIds.get(y.getId());
                            if (id == null) {
                                int oldId = y.getId();
                                updateActivity(y);
                                activitiesIds.put(oldId, y.getId());
                            } else {
                                y.setId(id);
                            }
                        }
                ));
    }

    private void updateActivities(Set<ActivityEntity> activityEntities) {
        activityEntities.forEach(this::updateActivity);
    }

    private void updateActivity(ActivityEntity activityEntity) {
        updateActivityUsers(activityEntity);
        activityEntity.setId(activityRepositoryDao.createActivity(activityEntity));
    }

    private void updateActivityUsers(ActivityEntity activityEntity) {
        activityEntity.setAssigned_users(
                updateUsers(activityEntity.getAssigned_users()));
    }

    private Set<UserEntity> updateUsers(Set<UserEntity> users) {
        return users.stream().map(this::updateUser).collect(Collectors.toSet());
    }

    private UserEntity updateUser(UserEntity userEntity) {
        int userId = userRepositoryDao.getUserIdByEmail(userEntity.getEmail());
        if (userId == 0) {
            userId = userRepositoryDao.createUser(userEntity.getName(), userEntity.getSurname(), userEntity.getEmail());
        }
        userEntity.setId(userId);
        return userEntity;
    }

    private Set<UserEntity> parseUserEntityFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        if (json != null) {
            try {
                UserEntity[] readValue = mapper.readValue(json, UserEntity[].class);
                Set<UserEntity> userEntitySet = new HashSet<>(Arrays.asList(readValue));
                return userEntitySet;
            } catch (JsonProcessingException e) {
                System.out.println("PROCESSING_FILE_ERROR");
            }
        } else {
            System.out.println("EMPTY_FILE");
        }
        return Collections.emptySet();
    }

    private Set<ActivityEntity> parseActivityEntityFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        if (json != null) {
            try {
                ActivityEntity[] readValue = mapper.readValue(json, ActivityEntity[].class);
                Set<ActivityEntity> activityEntitySet = new HashSet<>(Arrays.asList(readValue));
                return activityEntitySet;
            } catch (JsonProcessingException e) {
                System.out.println("PROCESSING_FILE_ERROR");
            }
        } else {
            System.out.println("EMPTY_FILE");
        }
        return Collections.emptySet();
    }

    private Set<PlanEntity> parsePlanEntityFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        if (json != null) {
            try {
                PlanEntity[] readValue = mapper.readValue(json, PlanEntity[].class);
                Set<PlanEntity> planEntitySet = new HashSet<>(Arrays.asList(readValue));
                return planEntitySet;
            } catch (JsonProcessingException e) {
                System.out.println("PROCESSING_FILE_ERROR");
            }
        } else {
            System.out.println("EMPTY_FILE");
        }
        return Collections.emptySet();
    }
}