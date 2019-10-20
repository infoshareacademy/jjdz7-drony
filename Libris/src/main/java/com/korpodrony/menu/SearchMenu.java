package com.korpodrony.menu;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;
import com.korpodrony.utils.IoTools;

import java.util.List;
import java.util.stream.Collectors;

public class SearchMenu {
    private MainMenu mainMenu;

    public SearchMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

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
        System.out.println("-- Szukanie użytkownika po imieniu - Wpisz imię:");
        String searchedText = IoTools.getStringFromUser().toLowerCase();
        List<User> users = mainMenu.dB.getAllUsers().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedText))
                .collect(Collectors.toList());
        if (users.isEmpty()) {
            System.out.println("Nie ma takiego użytkownika.");
            return;
        }
        users.sort(new UserIDComparator());
        users.forEach(System.out::println);
    }

    private void searchByActivityMenu() {
        System.out.println("-- Szukanie zajęć po nazwie - wpisz nazwę:");
        String searchedText = IoTools.getStringFromUser().toLowerCase();
        List<Activity> activities = mainMenu.dB.getAllActivities().stream().filter(x -> x.getName().toLowerCase().contains(searchedText)).collect(Collectors.toList());
        if (activities.isEmpty()) {
            System.out.println("Nie ma takich zajęć.");
            return;
        }
        activities.sort(new ActivityIDComparator());
        activities.forEach(System.out::println);
    }

    private void searchByScheduleMenu() {
        System.out.println("-- Szukanie planu po nazwie, wpisz nazwę:");
        String searchedText = IoTools.getStringFromUser().toLowerCase();
        List<Plan> plans = mainMenu.dB.getAllPlans().stream().filter(x -> x.getName().toLowerCase().contains(searchedText)).collect(Collectors.toList());
        if (plans.isEmpty()) {
            System.out.println("Nie ma takiego planu.");
            return;
        }
        plans.sort(new PlanIDComparator());
        plans.forEach(System.out::println);
    }
}
