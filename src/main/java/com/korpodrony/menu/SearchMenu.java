package com.korpodrony.menu;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.Activity;
import com.korpodrony.model.Plan;
import com.korpodrony.model.User;

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
            int choice = IoTools.getUserInput();
            runSearchMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private void runSearchMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startSearchByUserMenu();
                break;
            }
            case 2: {
                startSearchByActivityMenu();
                break;
            }
            case 3: {
                startSearchByScheduleMenu();
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

    private void startSearchByUserMenu() {
        System.out.println("Szukanie użytkownika po imieniu, wpisz imię");
        String searchedText = IoTools.readStringUserInput().toLowerCase();
        List<User> users = mainMenu.dB.getAllUsers().stream().filter(x -> x.getName().toLowerCase().contains(searchedText)).collect(Collectors.toList());
        if (users.size() == 0) {
            System.out.println("nie ma takiego użytkownika");
            return;
        }
        users.sort(new UserIDComparator());
        users.forEach(System.out::println);
    }

    private void startSearchByActivityMenu() {
        System.out.println("Szukanie zajęć po nazwie, wpisz nazwe");
        String searchedText = IoTools.readStringUserInput().toLowerCase();
        List<Activity> activities = mainMenu.dB.getAllActivies().stream().filter(x -> x.getName().toLowerCase().contains(searchedText)).collect(Collectors.toList());
        if (activities.size() == 0) {
            System.out.println("nie ma takich zajęć");
            return;
        }
        activities.sort(new ActivityIDComparator());
        activities.forEach(System.out::println);
    }

    private void startSearchByScheduleMenu() {
        System.out.println("Szukanie planu po naziwe, wpisz nazwe");
        String searchedText = IoTools.readStringUserInput().toLowerCase();
        List<Plan> plans = mainMenu.dB.getAllPlans().stream().filter(x -> x.getName().toLowerCase().contains(searchedText)).collect(Collectors.toList());
        if (plans.size() == 0) {
            System.out.println("nie ma takiego planu");
            return;
        }
        plans.sort(new PlanIDComparator());
        plans.forEach(System.out::println);
    }
}
