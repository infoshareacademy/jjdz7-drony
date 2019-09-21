package com.korpodrony;

import com.korpodrony.model.Organization;
import com.korpodrony.service.OrganizationService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Organization org = new Organization();
        OrganizationService os = new OrganizationService(org);
    }
}