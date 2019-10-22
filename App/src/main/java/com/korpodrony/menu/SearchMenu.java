package com.korpodrony.menu;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import com.korpodrony.service.ActivitiesService;
import com.korpodrony.service.PlansService;
import com.korpodrony.service.UserService;
import com.korpodrony.utils.IoTools;

import java.util.List;
import java.util.stream.Collectors;

public class SearchMenu {
    private UserService uS = new UserService();
    private ActivitiesService aS = new ActivitiesService();
    private PlansService pS = new PlansService();

    void startSearchMenu() {

        do {
            Messages.printSearchMenu();
            int choice = IoTools.getIntFromUser();
            decide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private void decide(int choice) {
        switch (choice) {
            case 1: {
                searchByUserMenu();
                break;
            }
            case 2: {
                searchByActivityMenu();
                break;
            }
            case 3: {
                searchByScheduleMenu();
                break;
            }
            case 4: {
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }

        }
    }

    private void searchByUserMenu() {
        System.out.println("-- Szukanie użytkownika po imieniu");
        uS.searchByUserMenu();
    }

    private void searchByActivityMenu() {
        System.out.println("-- Szukanie zajęć po nazwie");
        aS.searchByActivityMenu();
    }

    private void searchByScheduleMenu() {
        System.out.println("-- Szukanie planu po nazwie");
        pS.searchByScheduleMenu();
    }
}
