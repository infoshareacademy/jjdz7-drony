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
    private OrganizationRepositoryDao organization = new OrganizationRepositoryDaoImpl();

    public void loadParametersFromFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        Organization org = new Organization();
        if (path == null) {
            return;
        } else {
            if (Files.exists(Paths.get(path, "Users.json"))) {
                Set<User> usersFromJson = new JSONReader().parseUserFromJSONFile(Paths.get(path, "Users.json"));
                org.setUsers(usersFromJson);
                User.setCurrentID(usersFromJson);
            }
            if (Files.exists(Paths.get(path, "Activities.json"))) {
                Set<Activity> activtitiesFromJson = new JSONReader().parseActivityFromJSONFile(Paths.get(path, "Activities.json"));
                org.setActivities(activtitiesFromJson);
                Activity.setCurrentID(activtitiesFromJson);
            }
            if (Files.exists(Paths.get(path, "Plans.json"))) {
                Set<Plan> plansFromJson = new JSONReader().parsePlanFromJSONFile(Paths.get(path, "Plans.json"));
                org.setPlans(plansFromJson);
                Plan.setCurrentID(plansFromJson);
            }
            OrganizationRepository.setOrganizationRepository(org);
        }
    }

    public void writeRepositoryToFile() {
        String path = new PropertiesService().getProperty(PropertiesService.APP_PATH);
        if (path == null) {
            return;
        }
        JSONWriter.writeJSONToFile(Paths.get(path, "Users.json"), organization.getUsersSet());
        JSONWriter.writeJSONToFile(Paths.get(path, "Activities.json"), organization.getActivitiesSet());
        JSONWriter.writeJSONToFile(Paths.get(path, "Plans.json"), organization.getPlansSet());
    }
}
