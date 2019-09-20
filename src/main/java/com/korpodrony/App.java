package com.korpodrony;

import com.korpodrony.model.Organization;
import com.korpodrony.service.OrganizationService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        OrganizationService os = new OrganizationService(new Organization());
        os.addUser();
        os.addUser();
        os.addActivity();
        os.assignUserToActivity();
        os.unassignActivityFromPlan();
    }
}
