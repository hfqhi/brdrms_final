package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.impl.ResidentRepoImpl;
import com.joysistvi.brdrms.impl.UserRepoImpl;
import java.util.InputMismatchException;
import java.util.Scanner;
import static com.joysistvi.brdrms.view.AdminView.BLACK;
import static com.joysistvi.brdrms.view.AdminView.BLUE;
import static com.joysistvi.brdrms.view.AdminView.CYAN;
import static com.joysistvi.brdrms.view.AdminView.GREEN;
import static com.joysistvi.brdrms.view.AdminView.RED;
import static com.joysistvi.brdrms.view.AdminView.RESET;

public class ResidentView {

    private final Scanner scanner;
    private final UserRepoImpl userRepo;
    private final ResidentRepoImpl residentRepo;
    private final AdminView adminView;
    private final String fullname;

    public ResidentView(Scanner scanner, UserRepoImpl userRepo, ResidentRepoImpl residentRepo, AdminView adminView, String fullname) {
        this.scanner = scanner;
        this.userRepo = userRepo;
        this.residentRepo = residentRepo;
        this.adminView = adminView;
        this.fullname = fullname;
    }

    public void manageResidentMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println(CYAN + "                                                                             MANAGE RESIDENT   ");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [1]" + BLUE + " View Active Residents                                 | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [2]" + BLUE + " Add Resident                                          | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [3]" + BLUE + " Update Resident                                       | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [4]" + BLUE + " Delete Resident                                       | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [5]" + BLUE + " Archived Resident                                     | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [6]" + BLUE + " View Archived Residents                               |");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [7]" + BLUE + " Restore Resident                                      | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [8]" + BLUE + " Search Resident                                       | ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [0]" + BLUE + " Back                                                  |");
            System.out.print(BLACK + "\n                                                    Enter your Choice: ");
            try {
                int choice = scanner.nextInt();
                adminView.consumeBuffer();
                switch (choice) {
                    case 1:
                        System.out.println(BLUE + "\t\t\t\t\t\t\t\t\t* LIST OF ALL ACTIVE RESIDENTS * ");
                        adminView.displayResident(residentRepo.getResident());
                        break;
                    case 2:
                        adminView.createResident();
                        break;
                    case 3:
                        adminView.displayResident(residentRepo.getResident());
                        adminView.updateResidentInfo();
                        break;
                    case 4:
                        adminView.displayResident(residentRepo.getResident());
                        adminView.deleteResident();
                        break;
                    case 5:
                        adminView.displayResident(residentRepo.getResident());
                        adminView.archiveResident();
                        break;
                    case 6:
                        System.out.println(RED + "\t\t\t\t\t\t\t\t\t * LIST OF ALL INACTIVE RESIDENTS * ");
                        adminView.displayResident(residentRepo.getResidentArchived());
                        break;
                    case 7:
                        adminView.displayResident(residentRepo.getResidentArchived());
                        adminView.restoreResident();
                        break;
                    case 8:
                        adminView.searchResident();
                        break;
                    case 0:
                        System.out.println("\t\t\t\t\t\t\t\t Back to Main Menu...");
                        isRunning = false;
                        break;
                    default:
                        System.out.println(RED + "\t\t\t\t\t\t\t\t Invalid choice ");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t Invalid Input: Enter a valid number ");
            } catch (InputMismatchException e) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t Invalid Input: Enter a valid number ");
                adminView.consumeBuffer();
            }
        }
    }
}

