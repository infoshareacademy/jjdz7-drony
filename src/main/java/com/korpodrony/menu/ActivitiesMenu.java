package com.korpodrony.menu;

import com.korpodrony.comparators.UserIDComparator;
import com.korpodrony.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ActivitiesMenu {
    private MainMenu mainMenu;

    public ActivitiesMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void StartActivitiesMenu() {
        do {
            Messages.printActivitiesMenu();
            System.out.print("Twój wybór: ");
            int choice = IoTools.getIntFromUser();
            runActivitiesMenuDecide(choice);
        } while (!MainMenu.contextMenuExit);
    }

    private  void runActivitiesMenuDecide(int choice) {
        switch (choice) {
            case 1: {
                startActivitiesMenuAddActivity();
                break;
            }
            case 2: {
                startActivitiesMenuEditActivity();
                break;
            }
            case 3: {
                startActivitiesMenuDeleteActivity();
                break;
            }
            case 4: {
                startActivitiesMenuShowActivity();
                break;
            }
            case 5: {
                startActivitiesMenuAssignUser();
                break;
            }
            case 6: {
                startActivitiesMenuUnassignUser();
                break;
            }
            case 7: {
                startActivitiesMenuShowAssignedUsers();
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

    private void startActivitiesMenuShowAssignedUsers() {
        System.out.println("Pokazywanie użytkowników przypisanych do zajęć");
        mainMenu.dBService.printActivites();
        if (mainMenu.dB.getAllActivies().size() == 0) {
            return;
        }
        int choice = IoTools.getIntFromUserWithMessage("Podaj ID zajęć, których użytkowników chcesz obejrzeć");
        if (!mainMenu.dB.hasActivityWithThisID(choice)){
            System.out.println("Nie ma takich zajęć");
            return;
        }
        List<User> users = mainMenu.dB.getActivity(choice).getAssignedUsersIDs().stream().map(x->mainMenu.dB.getUser(x)).collect(Collectors.toList());
        if (users.size() == 0) {
            System.out.println("zajęcia nie mają przypisanych  żadnych użytkowników");
            return;
        }
        users.sort(new UserIDComparator());
        users.forEach(System.out::println);
    }

    private void startActivitiesMenuUnassignUser() {
        System.out.println("Wypisywanie użytkownika z zajęć");
        mainMenu.dBService.unassingUserFromActivity();
    }

    private void startActivitiesMenuAssignUser() {
        System.out.println("Przypisywanie użytkownika do zajęć");
        mainMenu.dBService.assignUserToActivity();
    }

    private void startActivitiesMenuAddActivity() {
        System.out.println("Dodawanie nowych zajęć");
        mainMenu.dBService.addActivity();
    }

    private void startActivitiesMenuEditActivity() {
        mainMenu.dBService.editActiity();
    }

    private void startActivitiesMenuDeleteActivity() {
        System.out.println("Usuwanie istniejących zajęć");
        mainMenu.dBService.removeActivity();
    }

    private void startActivitiesMenuShowActivity() {
        System.out.println("Pokazywanie istniejących zajęć");
        mainMenu.dBService.printActivites();
    }
}
