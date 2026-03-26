/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.model.User;
import com.joysistvi.brdrms.impl.UserRepoImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Hacutina
 */
public class RegisterView {

    private final Scanner scanner;
    private final UserRepoImpl userRepo;

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public RegisterView(Scanner scanner, UserRepoImpl userRepo) {
        this.scanner = scanner;
        this.userRepo = userRepo;
    }

    public void createUser() {
        User user = new User();
        System.out.println(BLUE + "                                                                                    REGISTRATION      \n" + RESET);
        while (true) {
            System.out.print("                                                                           Enter Username:       ");
            String username = scanner.nextLine();
            if (userRepo.isUsernameExist(username)) {
                System.out.println(RED + "                                                                              Username Is Already Used! \n"+ RESET);
            } else {
                user.setUserName(username);
                break;
            }
        }
        System.out.print("                                                                           Enter Password:       ");
        user.setPassword(scanner.nextLine());
        System.out.print("                                                                           Enter FullName:       ");
        user.setFullName(scanner.nextLine());
        System.out.print("                                                                           Enter Contact Number: ");
        user.setContactNumber(scanner.nextLine());
        System.out.print("                                                                           Enter Address:        ");
        user.setAddress(scanner.nextLine());

        if (userRepo.createUser(user)) {
            System.out.println(GREEN +"                                                                                  Success \n\n\n\n\n");

        } else {
            System.out.println(RED + "                                                                                   Failed \n\n\n\n\n");
        }
    }
}
