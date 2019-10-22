package com.korpodrony.menu;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.Activity;
import com.korpodrony.utils.IoTools;

import java.util.List;
import java.util.stream.Collectors;

public class SchedulesMenu {

    private MainMenu mainMenu;

    public SchedulesMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void startSchedulesMenu() {
        do {
            Messages.printSchedulesMenu();
            int choice = IoTools.getIntFromUser();
            decide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private void decide(int choice) {
        switch (choice) {
            case 1: {
                addSchedule();
                break;
            }
            case 2: {
                editSchedule();
                break;
            }
            case 3: {
                deleteSchedule();
                break;
            }
            case 4: {
                showSchedules();
                break;
            }
            case 5: {
                assignActivity();
                break;
            }
            case 6: {
                unassignActivity();
                break;
            }
            case 7: {
                showActivitiesOfSchedule();
                break;
            }
            case 8: {
                MainMenu.contextMenuExit = true;
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }
        }
    }
    //TODO take logic out of menu
    private void showActivitiesOfSchedule() {
        System.out.println("-- Pokazywanie zajęć przypisanych do planu --");
        mainMenu.dBService.printPlans();
        if (mainMenu.dB.getAllPlans().isEmpty()) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID planu, którego zajęcia chcesz obejrzeć: ");
        if (!mainMenu.dB.hasPlanWithThisID(choice)) {
            System.out.println("Nie ma takiego planu.");
            return;
        }
        List<Activity> activities = mainMenu.dB.getPlan(choice).getActivitiesID().stream().map(x -> mainMenu.dB.getActivity(x)).collect(Collectors.toList());
        if (activities.isEmpty()) {
            System.out.println("Plan nie ma przypisanych żadnych zajęć!");
            return;
        }
        activities.sort(new UserIDComparator());
        activities.forEach(System.out::println);
    }

    private void unassignActivity() {
        System.out.println("-- Usuwanie zajęć z planu --");
        mainMenu.dBService.unassignActivityFromPlan();
    }

    private void assignActivity() {
        System.out.println("-- Przypisywanie zajęc do planu --");
        mainMenu.dBService.assignActivityToPlan();
    }

    private void addSchedule() {
        System.out.println("-- Dodawanie nowych planów --");
        mainMenu.dBService.addPlan();
    }

    private void editSchedule() {
        System.out.println("-- Edytowanie istniejących planów --");
        mainMenu.dBService.editPlan();
    }

    private void deleteSchedule() {
        System.out.println("-- Usuwanie istniejących planów --");
        mainMenu.dBService.removePlan();
    }

    private void showSchedules() {
        System.out.println("-- Pokazywanie istniejących planów --");
        mainMenu.dBService.printPlans();
    }
}
