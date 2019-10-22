package com.korpodrony.menu;

import com.korpodrony.model.Organization;
import com.korpodrony.repository.OrganizationRepository;
import com.korpodrony.service.RepositoryService;
import com.korpodrony.utils.IoTools;

public class InitialMenu {

    public void startInitialMenu() {
        int choice = 0;
        do {
            Messages.printInitialMenu();
            choice = IoTools.getIntFromUser();
            decide(choice);
        } while (choice != 1 && choice != 2);
    }

    private void decide(int choice) {
        switch (choice) {
            case 1: {
                initialParametersLoad();
                break;
            }
            case 2: {
                createNewOrganization();
                break;
            }
            default: {
                Messages.printBadInputErrorMessage();
            }
        }
    }

    private void createNewOrganization() {
        Organization org = new Organization();
        OrganizationRepository.setOrganizationRepository(org);
    }

    private void initialParametersLoad() {
            new RepositoryService().loadParametersFromFile();
            new MainMenu().startMainMenu();
        }
}