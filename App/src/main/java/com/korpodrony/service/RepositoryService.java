package com.korpodrony.service;

import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.dao.OrganizationRepositoryDaoImpl;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import com.korpodrony.repository.OrganizationRepository;
import com.korpodrony.utils.JSONReader;
import com.korpodrony.utils.JSONWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class RepositoryService {

    private static final String USERS_JSON = "Users.json";
    private static final String ACTIVITIES_JSON = "Activities.json";
    private static final String PLANS_JSON = "Plans.json";
    private OrganizationRepositoryDao organization = new OrganizationRepositoryDaoImpl();

    public void writeRepositoryToFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        if (path == null) {
            return;
        }
        JSONWriter.writeJSONToFile(Paths.get(path, USERS_JSON), organization.getUsersSet());
        JSONWriter.writeJSONToFile(Paths.get(path, ACTIVITIES_JSON), organization.getActivitiesSet());
        JSONWriter.writeJSONToFile(Paths.get(path, PLANS_JSON), organization.getPlansSet());
    }

    public void loadParametersFromFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        Organization org = new Organization();
        if (path != null) {
            if (Files.exists(Paths.get(path, USERS_JSON))) {
                setUsersFromFile(path, org);
            }
            if (Files.exists(Paths.get(path, ACTIVITIES_JSON))) {
                setActivitiesFromFile(path, org);
            }
            if (Files.exists(Paths.get(path, PLANS_JSON))) {
                setPlansFromFile(path, org);
            }
        }
        OrganizationRepository.setOrganizationRepository(org);
    }

    private void setUsersFromFile(String path, Organization org) {
        Set<User> usersFromJson = JSONReader.parseUserFromJSONFile(Paths.get(path, USERS_JSON));
        org.setUsers(usersFromJson);
        User.setCurrentID(usersFromJson);
    }

    private void setActivitiesFromFile(String path, Organization org) {
        Set<Activity> activtitiesFromJson = JSONReader.parseActivityFromJSONFile(Paths.get(path, ACTIVITIES_JSON));
        org.setActivities(activtitiesFromJson);
        Activity.setCurrentID(activtitiesFromJson);
    }

    private void setPlansFromFile(String path, Organization org) {
        Set<Plan> plansFromJson = JSONReader.parsePlanFromJSONFile(Paths.get(path, PLANS_JSON));
        org.setPlans(plansFromJson);
        Plan.setCurrentID(plansFromJson);
    }
}