package com.korpodrony.utils;

import com.korpodrony.model.Activity;
import com.korpodrony.model.Organization;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class JSONReaderAndWriterTest {

    @Test
    public void testActivity() {

        // when
        JSONWriter jsonWriter = new JSONWriter();

        HashSet<Activity> objectToWrite = new HashSet<>();
        Set<Integer> assignedUsersIDs = new HashSet<>();
        assignedUsersIDs.add(23);
        Activity firstActivity = new Activity(1, "name1", (short) 23, assignedUsersIDs, (byte) 3);
        Activity secondActivity = new Activity(2, "name2", (short) 15, assignedUsersIDs, (byte) 2);

        objectToWrite.add(firstActivity);
        objectToWrite.add(secondActivity);

        // given
        Path fileToGenerate = Paths.get("/home/zuza/IdeaProjects/jjdz7-drony/jjdz7-drony/src/test/java/com/korpodrony/files/Activity");
        jsonWriter.writeJSONToFile(fileToGenerate, objectToWrite);

        // then
        JSONReader jsonReader = new JSONReader();
        Set<Activity> activity = jsonReader.parseActivityFromJSONFile(fileToGenerate);

        if (activity.contains(firstActivity)) {
            System.out.println("Activity dziala 1 !");
        }

        if (activity.contains(secondActivity)) {
            System.out.println("Activity dziala 2 !");
        }

    }

    @Test
    public void testOrganization() {


        // when
        JSONWriter jsonWriter = new JSONWriter();

        HashSet<Organization> objectToWrite = new HashSet<>();
        Set<User> users = new HashSet<>();
        users.add(new User(1, "Franciszek", "Kimono"));
        Set<Plan> plans = new HashSet<>();
        plans.add(new Plan("Moj plan"));
        Set<Activity> activities = new HashSet<>();
        activities.add(new Activity("name2", (short) 13, (byte) 2));
        Organization firstOrganization = new Organization();
        Organization secondOrganization = new Organization(users, plans, activities);
        objectToWrite.add(firstOrganization);
        objectToWrite.add(secondOrganization);

        // given
        Path fileToGenerate = Paths.get("/home/zuza/IdeaProjects/jjdz7-drony/jjdz7-drony/src/test/java/com/korpodrony/files/Organization");
        jsonWriter.writeJSONToFile(fileToGenerate, objectToWrite);

        // then
        JSONReader jsonReader = new JSONReader();
        Set<Organization> organization = jsonReader.parseOrganizationFromJSONFile(fileToGenerate);

        if (organization.contains(firstOrganization)) {
            System.out.println("Organization dziala 1 !");
        }

        if (organization.contains(secondOrganization)) {
            System.out.println("Organization dziala 2 !");
        }

    }

    @Test
    public void testPlan() {

        // when
        JSONWriter jsonWriter = new JSONWriter();

        HashSet<Plan> objectToWrite = new HashSet<>();
        Plan firstPlan = new Plan();
        Plan secondPlan = new Plan();
        objectToWrite.add(firstPlan);
        objectToWrite.add(secondPlan);

        // given
        Path fileToGenerate = Paths.get("/home/zuza/IdeaProjects/jjdz7-drony/jjdz7-drony/src/test/java/com/korpodrony/files/Plan");
        jsonWriter.writeJSONToFile(fileToGenerate, objectToWrite);

        // then
        JSONReader jsonReader = new JSONReader();
        Set<Plan> plans = jsonReader.parsePlanFromJSONFile(fileToGenerate);

        if (plans.contains(firstPlan)) {
            System.out.println("Plan dziala 1 !");
        }

        if (plans.contains(secondPlan)) {
            System.out.println("Plan dziala 2 !");
        }

    }

    @Test
    public void testUser() {

        // when
        JSONWriter jsonWriter = new JSONWriter();

        HashSet<User> objectToWrite = new HashSet<>();
        User firstUser = new User(1, "name1", "surename1");
        User secondUser = new User(2, "name2", "surename2");
        objectToWrite.add(firstUser);
        objectToWrite.add(secondUser);

        // given
        Path fileToGenerate = Paths.get("/home/zuza/IdeaProjects/jjdz7-drony/jjdz7-drony/src/test/java/com/korpodrony/files/Users");
        jsonWriter.writeJSONToFile(fileToGenerate, objectToWrite);

        // then
        JSONReader jsonReader = new JSONReader();
        Set<User> users = jsonReader.parseUserFromJSONFile(fileToGenerate);

        if (users.contains(firstUser)) {
            System.out.println("User dziala 1 !");
        }

        if (users.contains(secondUser)) {
            System.out.println("User dziala 2 !");
        }

    }
}