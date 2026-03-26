package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.impl.ResidentRepoImpl;
import com.joysistvi.brdrms.impl.UserRepoImpl;
import com.joysistvi.brdrms.model.User;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static com.joysistvi.brdrms.view.AdminView.BLACK;
import static com.joysistvi.brdrms.view.AdminView.BLUE;
import static com.joysistvi.brdrms.view.AdminView.CYAN;
import static com.joysistvi.brdrms.view.AdminView.GREEN;
import static com.joysistvi.brdrms.view.AdminView.RED;
import static com.joysistvi.brdrms.view.AdminView.RESET;

public class UserManagementView {

    private final Scanner scanner;
    private final UserRepoImpl userRepo;
    private final ResidentRepoImpl residentRepo;
    private final AdminView adminView;
    private final String fullname;

    public UserManagementView(Scanner scanner, UserRepoImpl userRepo, ResidentRepoImpl residentRepo, AdminView adminView, String fullname) {
        this.scanner = scanner;
        this.userRepo = userRepo;
        this.residentRepo = residentRepo;
        this.adminView = adminView;
        this.fullname = fullname;
    }

    public void manageAccountsMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println(CYAN + "                                                                            MANAGE ACCOUNT   ");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [1]" + BLUE + " View Accounts [APPROVED]                          |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [2]" + BLUE + " Add ADMIN Account                                 |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [3]" + BLUE + " Update Account Information                        |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [4]" + BLUE + " Delete Account                                    |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [5]" + BLUE + " View Pending Accounts                             |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [6]" + BLUE + " View Rejected Accounts                            |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [7]" + BLUE + " Search ACCOUNT                                    |            ");
            System.out.println(BLACK + "                                                 |                  " + CYAN + " [0]" + BLUE + " Back                                              |            ");
            System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
            System.out.print(BLACK + "                                                    Enter your Choice: ");
            try {
                int choice = scanner.nextInt();
                adminView.consumeBuffer();
                switch (choice) {
                    case 1:
                        System.out.println("\n\t\t\t\t\t\t\t\t\t\t " + BLUE + "* LIST OF ALL ACTIVE ACCOUNTs * ");
                        adminView.displayUser(userRepo.getUser());
                        break;
                    case 2:
                        System.out.println("\n\t\t\t\t\t\t\t\t\t " + CYAN + "   CREATE ADMIN ACCOUNT\n" + RESET);
                        adminView.createUserAdmin();
                        break;
                    case 3:
                        adminView.displayUser(userRepo.getUser());
                        adminView.updateUserInfo();
                        break;
                    case 4:
                        adminView.displayUser(userRepo.getUser());
                        adminView.deleteUser();
                        break;
                    case 5:
                        List<User> pendingUsers = userRepo.getPendingUser();
                        if (pendingUsers.isEmpty()) {
                            System.out.println(GREEN + "\n\t\t\t\t\t\t\t\t\t\t No Pending Users");
                            break;
                        } else {
                            adminView.displayPendingUser(userRepo.getPendingUser());
                            adminView.toApproveRejectUser();
                            break;
                        }
                    case 6:
                        System.out.println(RED + " \t\t\t\t\t\t\t\t\t\t\t   * LIST OF ALL REJECTED ACCOUNTS * ");
                        adminView.displayRejectedUser(userRepo.getRejectedUser());
                        break;
                    case 7:
                        adminView.searchUser();
                        break;
                    case 0:
                        System.out.println(BLACK + "Back to Main Menu...");
                        isRunning = false;
                        break;
                    default:
                        System.out.println(RED + " Invalid Choice Select [1-7]!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + " Invalid Input: Enter a Valid Number! ");
            } catch (InputMismatchException e) {
                System.out.println(RED + " Invalid Input: Enter a Valid Number! " + RESET);
                adminView.consumeBuffer();
            }
        }
    }
}

