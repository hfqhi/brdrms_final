/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms;

import com.joysistvi.brdrms.impl.DocumentRequestRepoImpl;
import com.joysistvi.brdrms.impl.DocTypeRepoImpl;
import com.joysistvi.brdrms.impl.UserRepoImpl;
import com.joysistvi.brdrms.impl.ResidentRepoImpl;
import com.joysistvi.brdrms.model.User;
import com.joysistvi.brdrms.util.DbConnection;
import com.joysistvi.brdrms.view.AdminView;
import com.joysistvi.brdrms.view.DocTypeView;
import com.joysistvi.brdrms.view.DocumentRequestView;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.joysistvi.brdrms.view.LoginView;
import com.joysistvi.brdrms.view.RegisterView;
import com.joysistvi.brdrms.view.UserView;
///////////ORI

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static DbConnection dbConnection = new DbConnection();
    static UserRepoImpl userRepo = new UserRepoImpl(dbConnection);
    static LoginView loginView = new LoginView(scanner);

    static ResidentRepoImpl residentRepo = new ResidentRepoImpl(dbConnection);
    static DocTypeRepoImpl docTypeRepo = new DocTypeRepoImpl(dbConnection);
    static DocumentRequestRepoImpl documentRequest = new DocumentRequestRepoImpl(dbConnection);

    public static void main(String[] args) {

        int choice = loginView.loginView();
        mainMenu(choice);

    }

    public static void mainMenu(int choice) {
        LoginView loginView = new LoginView(scanner);
        AdminView adminView = new AdminView(scanner, userRepo, residentRepo);
        UserView userView = new UserView(scanner);
        DocTypeView dt = new DocTypeView(scanner);

        DocumentRequestView documentRequestView = new DocumentRequestView(scanner);
        int currentChoice = choice;

        do {
            switch (currentChoice) {
                case 1:
                    boolean is_FailedLogin = true;
                    int attempts = 0;
                    int maxAttempts = 3;
                    do {
                        String username = loginView.usernameInput();
                        if (username != null) {
                            String u = username.trim();
                            if (u.equalsIgnoreCase("q") || u.equalsIgnoreCase("quit") || u.equals("0") || u.equalsIgnoreCase("back")) {
                                // Allow the user to escape the login loop even with repeated invalid input.
                                is_FailedLogin = false;
                                break;
                            }
                        }
                        String password = loginView.passwordInput();
                        String[] userDetails = userRepo.verifyLogin(username, password);

                        if (userDetails != null && userDetails[2] != null) {
                            if ("ADMIN".equals(userDetails[2])) {
                                loginView.showSuccessMessage();
                                is_FailedLogin = false;
                                boolean is_AdminExit = false;
                                do {
                                    int adminChoice = adminView.displayAdminView(userDetails[1]);
                                    switch (adminChoice) {
                                        case 1:
                                            documentRequestView.manageDocumentRequestMenu(Integer.parseInt(userDetails[0]));
                                            break;
                                        case 2:
                                            adminView.manageResidentMenu(residentRepo);
                                            break;
                                        case 3:
                                            adminView.manageAccountsMenu(userRepo);
                                            break;
                                        case 4:
                                            dt.manageDocTypeMenu(docTypeRepo);
                                            break;
                                        case 0:
                                            is_AdminExit = true;
                                            loginView.showLogoutMessage();
                                            break;
                                        default:
                                            break;
                                    }
                                } while (!is_AdminExit);

                            } else if ("USER".equals(userDetails[2])) {
                                loginView.showSuccessMessage();
                                is_FailedLogin = false;
                                boolean is_Exit = false;
                                do {
                                    int userChoice = userView.displayUserView(userDetails[1]);
                                    switch (userChoice) {
                                        case 1:
                                            //userView.getDocumentRequestView(documentRequest.fetchDocumentRequestById(Integer.parseInt(userDetails[0])));
                                            userView.viewDocumentRequest(Integer.parseInt(userDetails[0]));
                                            break;
                                        case 2:
                                            userView.createDocumentRequestView(Integer.parseInt(userDetails[0]));
                                           break;
                                        case 3:
                                            userView.cancelDocumentRequest(Integer.parseInt(userDetails[0]));
                                            break;
                                        case 4:
                                            userView.deleteDocumentRequest(Integer.parseInt(userDetails[0]));
                                            break;
                                        case 5:
                                            userView.getUserDetails(userRepo.getUserViewById(Integer.parseInt(userDetails[0])));
                                            break;
                                        case 6:
                                            boolean is_Logout = loginView.getConfirmation();
                                            if (is_Logout) {
                                                // Return to the login menu (outer loop will re-prompt)
                                                is_Exit = true;
                                            }
                                            break;
                                        case 7:
                                            is_Exit = true;
                                            loginView.systemExit();
                                            break;
                                        default:
                                            System.out.println("Invalid Input!");
                                    }
                                } while (!is_Exit);

                            } else {
                                loginView.showErrorMessage();
                                attempts++;
                                if (attempts >= maxAttempts) {
                                    System.out.println("Too many invalid login attempts. Returning to the main menu...");
                                    is_FailedLogin = false;
                                }
                            }
                        } else {
                            loginView.showErrorMessage();
                            attempts++;
                            if (attempts >= maxAttempts) {
                                System.out.println("Too many invalid login attempts. Returning to the main menu...");
                                is_FailedLogin = false;
                            }
                        }

                    } while (is_FailedLogin);

                    break;
                case 2:
                    RegisterView registerView = new RegisterView(scanner, userRepo);
                    registerView.createUser();
                    break;
                case 3:
                    loginView.systemExit();
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
            currentChoice = loginView.loginView();
        } while (true);
    }
}
