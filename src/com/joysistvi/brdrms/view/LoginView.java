/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.view;

/**
 *
 * @author Hacutina
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginView {

    private final Scanner scanner;

    // Reset
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
  


    public LoginView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int loginView() {
     
        System.out.println("\n\n");
        System.out.println(BLUE + "                                                  ██████╗  ██████╗ ██████╗  ██████╗  ███╗ ███╗███████╗");
        System.out.println(BLUE + "                                                  ██╔══██╗██╔══██╗██╔══██╗██╔══██╗████████╗██╔════╝");
        System.out.println(BLUE + "                                                  ██████╔╝██████╔╝██║   ██║██████╔╝██╔██╔██║███████╗");
        System.out.println(BLUE + "                                                  ██╔══██╗██╔══██╗██║   ██║██╔══██╗██║╚═╝██║╚════██║");
        System.out.println(BLUE + "                                                  ██████╔╝██║   ██║██████╔╝██║   ██║██║     ██║███████║");
        System.out.println(BLUE + "                                                  ╚═════╝ ╚═╝    ╚═╝╚═════╝ ╚═╝   ╚═╝╚═╝     ╚═╝╚══════╝");
        
        System.out.println(CYAN + "                                                                            WELCOME TO BARANGAY JOYSIS            \n");
        System.out.println(BLACK + "╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
        System.out.println(BLACK + "                                               |                    "+ CYAN +" [1]" + BLUE + " Login                                                  |            ");
        System.out.println(BLACK + "                                               |                    "+ CYAN +" [2]" + BLUE + " Create Account                                         |            ");
        System.out.println(BLACK + "                                               |                    "+ CYAN +" [3]" + BLUE + " Exit                                                   |           \n");
        System.out.println(BLACK + "╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
        
        int choice = 0;
        boolean is_validInput = false;
        do {
            try {
                System.out.print(BLACK + "                                                    Enter your choice: ");
                // If input is not an integer, throws InputMismatchException
                choice = scanner.nextInt(); 
                scanner.nextLine();
                if (choice >= 1 && choice <= 3) {
                    is_validInput = true;
                } else{
                    System.out.println(RED + "                                                          Please Select In The Range of 1-3 Only          \n" + RESET);
                }
            } catch (InputMismatchException e) {
                showInvalidInput();
                scanner.nextLine(); // Clear the invalid input from scanner buffer
            }
          } while (!is_validInput);
        
     
        System.out.println(BLACK + "\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
        return choice;
    }
    

    public String usernameInput() {
        System.out.println(BLUE + "                                                                                      LOGIN                                      " + RESET);
        System.out.print("                                                                         Username: ");
        String username = scanner.nextLine();
       
        return username;
    }

    public String passwordInput() {
        System.out.print("                                                                         Password: ");
        String password = scanner.nextLine();
        

        return password;
    }
    
    public boolean getConfirmation() {

        boolean is_validInput = false;
        boolean is_successRequest = false;
        String choice;
        do {
            try {
                System.out.print("Are you sure you want to log-out? [Y] Yes or [N] No: ");
                choice = scanner.nextLine();
                if (String.valueOf(choice.charAt(0)).toLowerCase().equals("y")) {
                    System.out.println(BLUE + "You have been logged out. Thank you!");
                    System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
                    is_validInput = true;
                    is_successRequest = true;
                } else if (String.valueOf(choice.charAt(0)).toLowerCase().equals("n")) {
                    System.out.println(RED + "Returning to Main Menu");
                    //displayUserView(loginFullName);
                    is_validInput = true;
                    is_successRequest = false;
                } else {
                    System.out.println(RED + "Please select a valid input!");
                }

            } catch (StringIndexOutOfBoundsException e) {
                showInvalidInput();
            }
        } while (!is_validInput);

        return is_successRequest;

    }

    public void showSuccessMessage() {
        System.out.println(GREEN + "                                                                       You have successfully login!                      \n\n\n\n\n\n\n\n\n\n\n\n\n" + RESET );
    }

    public void showErrorMessage() {
        System.out.println(RED + "\n                                                                 You have entered an invalid username or password! " + RESET);
        System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
    }

    public void showInvalidInput() {
        System.out.println(RED + "                                                                   Invalid input. Please enter a valid integer!      " + RESET);
    }
    
    public void consumeBuffer(){
        scanner.nextLine();
    }
    
    public void showLogoutMessage(){
        System.out.println(BLUE + "                                                                You have been logged out. Thank you!" + RESET );
        System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
    }

    public void systemExit(){
        System.out.println(BLUE + "                                                                Exiting..... Thank you!" + RESET);
        System.exit(0);
    }
}
 