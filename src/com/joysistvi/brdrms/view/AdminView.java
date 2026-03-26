/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.model.Resident;
import com.joysistvi.brdrms.model.User;
import com.joysistvi.brdrms.impl.DocTypeRepoImpl;
import com.joysistvi.brdrms.impl.ResidentRepoImpl;
import com.joysistvi.brdrms.impl.UserRepoImpl;
import com.joysistvi.brdrms.util.DbConnection;
import static com.joysistvi.brdrms.view.LoginView.BLACK;
import static com.joysistvi.brdrms.view.LoginView.BLUE;
import static com.joysistvi.brdrms.view.LoginView.CYAN;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Hacutina
 */
public class AdminView {

    private final Scanner scanner;
    private final UserRepoImpl userRepo;
    private final ResidentRepoImpl residentRepo;

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    private String fullname;

    public AdminView(Scanner scanner, UserRepoImpl userRepo, ResidentRepoImpl residentRepo) {
        this.scanner = scanner;
        this.userRepo = userRepo;
        this.residentRepo = residentRepo;
    }

    public int displayAdminView(String name) {

        fullname = name;
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println(CYAN + "                                                                             WELCOME " + fullname.toUpperCase() + "!                             " + RESET);

        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
        System.out.println(BLACK + "                                                 |                  " + CYAN + " [1]" + BLUE + " Manage Request                                        |            ");
        System.out.println(BLACK + "                                                 |                  " + CYAN + " [2]" + BLUE + " Manage Residents                                      |            ");
        System.out.println(BLACK + "                                                 |                  " + CYAN + " [3]" + BLUE + " Manage Accounts                                       |            ");
        System.out.println(BLACK + "                                                 |                  " + CYAN + " [4]" + BLUE + " Manage Document Type                                  |            ");
        System.out.println(BLACK + "                                                 |                  " + CYAN + " [0]" + BLUE + " Exit                                                  |            ");

        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");

        int choice = 0;
        try {
            System.out.print(BLACK + "                                                    Enter your choice: ");
            // If input is not an integer, throws InputMismatchException
            choice = scanner.nextInt();
            consumeBuffer();

        } catch (InputMismatchException e) {
            System.out.println(RED + "                                                          Invalid input. Please enter a valid integer.               ");
            consumeBuffer(); // Clear the invalid input from scanner buffer
        }
        return choice;
    }

    // ACCOUNTS 
    public void manageAccountsMenu(UserRepoImpl userRepo) {
        UserManagementView userManagementView = new UserManagementView(scanner, userRepo, residentRepo, this, fullname);
        userManagementView.manageAccountsMenu();
    }

    public void displayUser(List<User> Users) {

        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %-30s| %n", "ID", "Username", "Password", "Full Name", "Contact Number", "Address", "Role", "Date_Status");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        for (User user : Users) {
            int id = user.getId();
            String userName = user.getUserName();
            String password = "*****************";
            String fullName = user.getFullName();
            String contactNumber = user.getContactNumber();
            String address = user.getAddress();
            String role = user.getRole();
            Timestamp dateStatus = user.getDateStatus();

            System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s|%-30s| %n", id, userName, password, fullName, contactNumber, address, role, dateStatus);

        }

    }

    public void displayAllUser(List<User> Users) {

        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %-10s| %-30s| %n", "ID", "Username", "Password", "Full Name", "Contact Number", "Address", "Role", "Status", "Date Status");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        for (User user : Users) {
            int id = user.getId();
            String userName = user.getUserName();
            String password = "*****************";
            String fullName = user.getFullName();
            String contactNumber = user.getContactNumber();
            String address = user.getAddress();
            String role = user.getRole();
            String status = user.getStatus();
            Timestamp dateStatus = user.getDateStatus();

            System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %-10s| %-30s| %n", id, userName, password, fullName, contactNumber, address, role, status, dateStatus);

        }

    }

    public void displayPendingUser(List<User> Users) {

        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %n", "ID", "Username", "Password", "Full Name", "Contact Number", "Address", "Role");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        for (User user : Users) {
            int id = user.getId();
            String userName = user.getUserName();
            String password = "*****************";
            String fullName = user.getFullName();
            String contactNumber = user.getContactNumber();
            String address = user.getAddress();
            String role = user.getRole();

            System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %n", id, userName, password, fullName, contactNumber, address, role);

        }

    }

    public void displayRejectedUser(List<User> Users) {

        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %-30s|%n", "ID", "Username", "Password", "Full Name", "Contact Number", "Address", "Role", "Date Status");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        for (User user : Users) {
            int id = user.getId();
            String userName = user.getUserName();
            String password = "*****************";
            String fullName = user.getFullName();
            String contactNumber = user.getContactNumber();
            String address = user.getAddress();
            String role = user.getRole();
            Timestamp dateStatus = user.getDateStatus();

            System.out.printf("%-5s| %-20s| %-60s| %-30s| %-15s| %-20s| %-10s| %-30s| %n", id, userName, password, fullName, contactNumber, address, role, dateStatus);

        }

    }

    public void createUser() {
        User user = new User();
        System.out.println(BLUE + " Creating User");
        while (true) {
            System.out.print(BLACK + " Enter Username       : ");
            String username = scanner.nextLine();
            if (userRepo.isUsernameExist(username)) {
                System.out.println(BLUE + " Username Is Already Used! ");
            } else {
                user.setUserName(username);
                break;
            }
        }
        System.out.print(BLACK + " Enter Password        : ");
        user.setPassword(scanner.nextLine());
        System.out.print(BLACK + " Enter FullName      : ");
        user.setFullName(scanner.nextLine());
        String contactNumber;
        while (true) {
            System.out.print(BLACK + " Enter Contact Number    : ");
            contactNumber = scanner.nextLine().trim();
            if (contactNumber.length() == 11) {
                user.setContactNumber(contactNumber);
                break;
            } else {
                System.out.println(RED + " Contact Number must have 11 digits! ");
            }
        }
        System.out.print(BLACK + " Enter Address      : ");
        user.setAddress(scanner.nextLine());
        user.setRole("USER");
        user.setStatus("APPROVED");

        if (confirmation("Are you sure you want to CREATE this USER? ")) {
            if (userRepo.createUser(user)) {
                System.out.println(GREEN + "USER Created Succesfully");
            } else {
                System.out.println(RED + "Creation of USER Failed");
            }
        } else {
            System.out.println(RED + "Creation of USER Cancelled");
        }
    }

    public void createUserAdmin() {
        User user = new User();
        while (true) {
            System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter Username:       ");
            String username = scanner.nextLine();
            if (userRepo.isUsernameExist(username)) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Username Is Already Used! ");
            } else {
                user.setUserName(username);
                break;
            }
        }
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter Password:       ");
        user.setPassword(scanner.nextLine());
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter FullName:       ");
        user.setFullName(scanner.nextLine());
        String contactNumber;
        while (true) {
            System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter Contact Number: ");
            contactNumber = scanner.nextLine().trim();
            if (contactNumber.length() == 11) {
                user.setContactNumber(contactNumber);
                break;
            } else {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Contact Number must have 11 digits! " + RESET);
            }
        }
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter Address:        ");
        user.setAddress(scanner.nextLine());
        user.setRole("ADMIN");
        user.setStatus("APPROVED");

        if (confirmation(BLUE + "\t\t\t\t\t\t\t  Are you sure you want to CREATE this ADMIN? ")) {
            if (userRepo.createUserAdmin(user)) {
                System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t  ADMIN Created Succesfully");
            } else {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Creation of ADMIN Failed");
            }
        } else {
            System.out.println(RED + "\t\t\t\t\t\t\t\t\t  Creation of ADMIN Cancelled");
        }

    }

    public void updateUserInfo() {
        System.out.print(BLUE + "\t\t\t\t\t\t\t\t  Enter User ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        User existingUser = userRepo.getUserById(id);

        if (existingUser != null) {
            while (true) {
                System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter New Username [" + existingUser.getUserName() + "]:            ");
                String newUserName = scanner.nextLine();
                if (newUserName.isEmpty()) {

                    break;
                }
                if (userRepo.isUsernameExist(newUserName)) {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t\t  Username is already used!");
                } else {
                    existingUser.setUserName(newUserName);
                    break;
                }
            }

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t  Enter New Password :                     ");
            String newPassword = scanner.nextLine();

            if (!newPassword.trim().isEmpty()) {
                String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                existingUser.setPassword(hashed);

            }

            System.out.print("\t\t\t\t\t\t\t\t  Enter New Fullname  [" + existingUser.getFullName() + "]:       ");
            String newFullName = scanner.nextLine();

            if (!newFullName.trim().isEmpty()) {
                existingUser.setFullName(newFullName);

            }

            while (true) {
                System.out.print("\t\t\t\t\t\t\t\t  Enter New Contact Number [" + existingUser.getContactNumber() + "] : ");
                String newContactNumber = scanner.nextLine();
                if (newContactNumber.isEmpty()) {
                    break;
                }
                if (newContactNumber.length() != 11) {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t\t  Contact Number must have 11 digits!");
                } else {

                    existingUser.setContactNumber(newContactNumber);
                    break;
                }
            }

            System.out.print("\t\t\t\t\t\t\t\t  Enter New Address [" + existingUser.getAddress() + "]:             ");
            String newAddress = scanner.nextLine();

            if (!newAddress.trim().isEmpty()) {
                existingUser.setAddress(newAddress);

            }

            if (confirmation(BLUE + "\t\t\t\t\t\t\t\t  Are you sure you want to Update userID: " + existingUser.getId() + "? ")) {
                if (userRepo.updateUserInfo(existingUser)) {
                    System.out.println(GREEN + "\t\t\t\t\t\t\t\t  Update for User with UserID: " + existingUser.getId() + " is Updated Succesfully");
                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t  Update for User with UserID: " + existingUser.getId() + " Failed");
                }
            } else {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Update for User with UserID: " + existingUser.getId() + " Cancelled");
            }

        } else {
            System.out.println(RED + "\t\t\t\t\t\t\t\t  Resident not found!");
        }
    }

    public void deleteUser() {

        System.out.print(BLUE + "\t\t\t\t\t\t\t\t  Enter User ID to delete: ");
        int delId = Integer.parseInt(scanner.nextLine());
        User existingUser = userRepo.getUserById(delId);
        if (existingUser != null) {
            if (confirmation(BLUE + "\t\t\t\t\t\t\t\t  Are you sure you want to DELETE this USER? " + RESET)) {
                if (userRepo.deleteUser(delId)) {
                    System.out.println(GREEN + "\t\t\t\t\t\t\t\t  User Deleted Succesfully");
                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t  Deletion of User Failed");
                }
            } else {
                System.out.println(GREEN + "\t\t\t\t\t\t\t\t  Deletion of User Cancelled");
            }
        } else {
            System.out.println(RED + "\t\t\t\t\t\t\t\t  User not found!");
        }
    }

    public void toApproveRejectUser() {

        System.out.print(BLUE + "\t\t\t\t\t\t\t\t  Enter User ID to Approve or Reject  : ");
        int selectedId = Integer.parseInt(scanner.nextLine());
        User existingUser = userRepo.getPendingUserById(selectedId);
        if (existingUser != null) {
            System.out.println(CYAN + "\t\t\t\t\t\t\t\t\t [1]" + BLUE + " APPROVE  ");
            System.out.println(CYAN + "\t\t\t\t\t\t\t\t\t [2]" + BLUE + " REJECT ");
            System.out.println(CYAN + "\t\t\t\t\t\t\t\t\t [3]" + BLUE + " BACK");
            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter your choice: ");
            int choice = scanner.nextInt();
            consumeBuffer();
            switch (choice) {
                case 1:
                    if (confirmation("\t\t\t\t\t\t\t\t  Are you sure you want to APPROVE this USER? ")) {
                        userRepo.approveUser(selectedId);
                        System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t\t User Approved");
                    } else {
                        System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t\t User Approval Cancelled");
                    }
                    break;

                case 2:
                    if (confirmation("\t\t\t\t\t\t\t\t  Are you sure you want to REJECT this USER? ")) {
                        userRepo.rejectUser(selectedId);
                        System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t\t User REJECTED");
                    } else {
                        System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t\t User REJECTION Cancelled");
                    }
                    break;
                case 3:

                    break;

                default:
                    System.out.println(BLACK + "\t\t\t\t\t\t\t\t\t\t\t Select [1-3]: ");
            }
        } else {
            System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t\t User not found!");
        }
    }

    public void searchUser() {
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t\t Enter Keyword to search: ");
        String keyword = scanner.nextLine();

        List<User> searchResults = userRepo.searchUser(keyword);

        if (searchResults.isEmpty()) {
            System.out.println(BLUE + "\t\t\t\t\t\t\t\t\t\t\t No User found.");
        } else {
            displayAllUser(searchResults);
        }
    }

    /// RESIDENT 
    public void manageResidentMenu(ResidentRepoImpl residentRepo) {
        ResidentView residentView = new ResidentView(scanner, userRepo, residentRepo, this, fullname);
        residentView.manageResidentMenu();
    }

    public void displayResident(List<Resident> Residents) {

        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("%-5s| %-20s| %-20s| %-20s| %-15s| %-8s| %-10s| %-13s| %-40s| %-30s| %-20s| %n", "ID", "FirstName", "LastName", "MiddleName", "BirthDate", "Gender", "Civil Status", "Contact Number", "Email", "Address", "Date Registered");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        for (Resident resident : Residents) {
            int id = resident.getId();
            String firstName = resident.getFirstName();
            String lastName = resident.getLastName();
            String middleName = resident.getMiddleName();
            String birthDate = resident.getBirthDate();
            String gender = resident.getGender();
            String civilStatus = resident.getCivilStatus();
            String contactNumber = resident.getContactNumber();
            String email = resident.getEmail();
            String address = resident.getAddress();
            Timestamp dateRegistered = resident.getDateRegistered();

            System.out.printf("%-5s| %-20s| %-20s| %-20s| %-15s| %-8s| %-10s| %-13s| %-40s| %-30s| %-20s| %n", id, firstName, lastName, middleName, birthDate, gender, civilStatus,
                    contactNumber, email, address, dateRegistered);

        }

    }

    public void createResident() {
        int currentYear = LocalDate.now().getYear();
        Map<Integer, Integer> monthDays = new HashMap<>();
        monthDays.put(1, 31);
        monthDays.put(2, 28);   // No leapyear
        monthDays.put(3, 31);
        monthDays.put(4, 30);
        monthDays.put(5, 31);
        monthDays.put(6, 30);
        monthDays.put(7, 31);
        monthDays.put(8, 31);
        monthDays.put(9, 30);
        monthDays.put(10, 31);
        monthDays.put(11, 30);
        monthDays.put(12, 31);

        Resident resident = new Resident();
        System.out.println(BLUE + "\t\t\t\t\t\t\t\t\t REGISTRATION OF RESIDENT     " + RESET);
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter FirstName:           ");
        resident.setFirstName(scanner.nextLine());
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter LastName:            ");
        resident.setLastName(scanner.nextLine());
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter MiddleName:          ");
        resident.setMiddleName(scanner.nextLine());

        int birthYear = 0;
        while (true) {
            try {
                System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Birth Year:          ");
                String bYear = scanner.nextLine();
                if (bYear.length() == 4) {
                    int nYear = Integer.parseInt(bYear);
                    if (nYear <= currentYear && nYear > 1800) {
                        birthYear = nYear;
                        break;
                    } else {
                        System.out.println(RED + "\t\t\t\t\t\t\t\t   Invalid Input: Invalid Year");
                    }
                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid Input: Invalid Year length.");
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid Input: Enter Numbers");
            }
        }
        int birthMonth = 0;
        while (true) {
            try {
                System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Birth Month [1-12]:  ");
                int inputMonth = Integer.parseInt(scanner.nextLine());
                if (inputMonth >= 1 && inputMonth <= 12) {
                    birthMonth = inputMonth;
                    break;
                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid month: Must be 1–12.");
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid Input: Enter Numbers");
            }
        }

        int birthDay = 0;
        int maximumDays = monthDays.get(birthMonth);
        while (true) {
            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Birth Day [1-" + maximumDays + "]:    ");
            try {
                int inputDay = Integer.parseInt(scanner.nextLine());
                if (inputDay >= 1 && inputDay <= maximumDays) {
                    birthDay = inputDay;
                    break;
                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid Day: Must be 1–" + maximumDays);
                }
            } catch (DateTimeException e) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid day for the given month/year.");
            } catch (NumberFormatException e) {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid Input: Enter Numbers ");
            }
        }

        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
        resident.setBirthDate(birthDate.toString());

        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Gender:              ");
        resident.setGender(scanner.nextLine());
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Civil Status:        ");
        resident.setCivilStatus(scanner.nextLine());

        String contactNumber;
        while (true) {
            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Contact Number:      ");
            contactNumber = scanner.nextLine().trim();
            if (contactNumber.length() == 11) {
                resident.setContactNumber(contactNumber);
                break;
            } else {
                System.out.println(RED + "\t\t\t\t\t\t\t\t  Invalid Input: Invalid Contact Number. ");
            }
        }
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Email:               ");
        resident.setEmail(scanner.nextLine());
        System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter Address:             ");
        resident.setAddress(scanner.nextLine());

        if (confirmation(BLUE + "\t\t\t\t\t\t\t\t Are you sure you want to add this resident? " + RESET)) {
            if (residentRepo.createResident(resident)) {
                System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t Resident Added Succesfully");
            } else {
                System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Resident Addition Failed");
            }
        } else {
            System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Additon of Resident Cancelled");
        }

    }

    public void updateResidentInfo() {
        System.out.print(BLUE + "\n\t\t\t\t\t\t\t Enter Resident ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Resident existingResident = residentRepo.getResidentById(id);

        if (existingResident != null) {

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Firstname [" + existingResident.getFirstName() + "]: ");
            String newFirstName = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Lastname [" + existingResident.getLastName() + "]: ");
            String newLastName = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Middlename [" + existingResident.getMiddleName() + "]: ");
            String newMiddleName = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Birthdate [" + existingResident.getBirthDate() + "]: ");
            String newBirthDate = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Gender [" + existingResident.getGender() + "]: ");
            String newGender = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Civil Status [" + existingResident.getCivilStatus() + "]: ");
            String newCivilStatus = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Contact Number [" + existingResident.getContactNumber() + "]: ");
            String newContactNumber = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Email [" + existingResident.getEmail() + "]: ");
            String newEmail = scanner.nextLine();

            System.out.print(BLACK + "\t\t\t\t\t\t\t\t Enter New Address [" + existingResident.getAddress() + "]: ");
            String newAddress = scanner.nextLine();

            if (!newFirstName.trim().isEmpty()) {
                existingResident.setFirstName(newFirstName);
            }
            if (!newLastName.trim().isEmpty()) {
                existingResident.setLastName(newLastName);
            }
            if (!newMiddleName.trim().isEmpty()) {
                existingResident.setMiddleName(newMiddleName);
            }
            if (!newBirthDate.trim().isEmpty()) {
                existingResident.setBirthDate(newBirthDate);
            }
            if (!newGender.trim().isEmpty()) {
                existingResident.setGender(newGender);
            }
            if (!newCivilStatus.trim().isEmpty()) {
                existingResident.setCivilStatus(newCivilStatus);
            }
            if (!newContactNumber.trim().isEmpty()) {
                existingResident.setContactNumber(newContactNumber);
            }
            if (!newEmail.trim().isEmpty()) {
                existingResident.setEmail(newEmail);
            }
            if (!newAddress.trim().isEmpty()) {
                existingResident.setAddress(newAddress);
            }

            if (confirmation(BLUE + "\t\t\t\t\t\t\t  Are you sure you want to update this resident? " + RESET)) {
                if (residentRepo.updateResidentInfo(existingResident)) {
                    System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t Resident Updated Succesfully");

                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Resident Update Failed");
                }
            } else {
                System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Update Cancelled");
            }
        } else {
            System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Resident not found!");
        }
    }

    public void deleteResident() {
        System.out.print(BLUE + "\n\t\t\t\t\t\t\t\t Enter Resident ID to delete: ");
        int delId = Integer.parseInt(scanner.nextLine());
        Resident existingResident = residentRepo.getResidentById(delId);
        if (existingResident != null) {
            if (confirmation(GREEN + "\t\t\t\t\t\t\t\t Are you sure you want to delete this resident? " + RESET)) {
                if (residentRepo.deleteResident(delId)) {
                    System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t Resident Deleted Succesfully");

                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Resident Deletion Failed");
                }
            } else {
                System.out.println(BLUE + "\t\t\t\t\t\t\t\t\t\t Deletion of Resident Cancelled");
            }
        } else {
            System.out.println(BLUE + "\t\t\t\t\t\t\t\t\t\t Resident not found!");
        }
    }

    public void archiveResident() {
        System.out.print(BLACK + "\n\t\t\t\t\t\t\t\t Enter Resident ID to archive: ");
        int archivedID = Integer.parseInt(scanner.nextLine());
        Resident existingResident = residentRepo.getResidentById(archivedID);
        if (existingResident != null) {
            if (confirmation(BLUE + "\t\t\t\t\t\t\t\t\t Are you sure you want to archive this resident? " + RESET)) {
                if (residentRepo.archiveResident(archivedID)) {
                    System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t  Resident Archived Succesfully");

                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t\t   Resident Archive Failed");
                }
            } else {
                System.out.println(BLACK + "\t\t\t\t\t\t\t\t\t   Archiving of Resident Cancelled");
            }
        } else {
            System.out.println(BLACK + "\t\t\t\t\t\t\t\t\t   Resident not found!");
        }
    }

    public void restoreResident() {
        System.out.print(BLACK + "\n\t\t\t\t\t\t\t\t Enter Resident ID to restore: ");
        int restoreID = Integer.parseInt(scanner.nextLine());
        Resident existingResident = residentRepo.getArchivedResidentById(restoreID);
        if (existingResident != null) {
            if (confirmation(BLUE + "\t\t\t\t\t\t\t\t Are you sure you want to restore this resident? " + RESET)) {
                if (residentRepo.restoreResident(restoreID)) {
                    System.out.println(GREEN + "\t\t\t\t\t\t\t\t\t\t Resident Restored Succesfully");
                } else {
                    System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t Resident Restoration Failed");
                }
            } else {
                System.out.println(BLACK + "\t\t\t\t\t\t\t\t\t\t Restoration of Resident Cancelled");
            }
        } else {
            System.out.println(BLACK + "\t\t\t\t\t\t\t\t\t\t Resident not found!");
        }
    }

    public void searchResident() {
        System.out.print(BLACK + "\n\t\t\t\t\t\t\t\t\t  Enter name to search: ");
        String keyword = scanner.nextLine();

        List<Resident> searchResults = residentRepo.searchResident(keyword);

        if (searchResults.isEmpty()) {
            System.out.println(RED + "\t\t\t\t\t\t\t\t\t\t\t\t No Residents Dound.");
        } else {
            displayResident(searchResults);
        }
    }

    public boolean confirmation(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm.equals("Y") || confirm.equals("YES")) {
                return true;
            } else if (confirm.equals("N") || confirm.equals("NO")) {
                return false;
            } else {
                System.out.println(RED + " Invalid Input. Enter " + CYAN + " Y or N.");
            }
        }
    }

    public void consumeBuffer() {
        scanner.nextLine();

    }

}
