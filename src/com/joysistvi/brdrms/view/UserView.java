/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.model.DocumentRequest;
import com.joysistvi.brdrms.model.DocumentType;
import com.joysistvi.brdrms.model.User;
import com.joysistvi.brdrms.impl.DocTypeRepoImpl;
import com.joysistvi.brdrms.impl.DocumentRequestRepoImpl;
import com.joysistvi.brdrms.util.DbConnection;
//import static com.joysistvi.brdrms.view.LoginView.RED;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Hacutina
 */
public class UserView {

    private final Scanner scanner;
    private static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLACK = "\u001B[30m";
    public static final String ORANGE = "\u001B[38;5;208m";

    private String loginFullName = "";
    private int loginUserId = 0;

    private static DbConnection dbConnect = new DbConnection();

    private DocumentRequestRepoImpl documentRequest = new DocumentRequestRepoImpl(dbConnect);

    public UserView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int displayUserView(String username) {
        // ANSI Colors
        loginFullName = username;
        System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println(BLUE + "                                                                    WELCOME " + loginFullName.toUpperCase() + "!   ");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [1]" + BLUE + " View Document Request                           |            ");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [2]" + BLUE + " Create Document Request                         |            ");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [3]" + BLUE + " Cancel Pending Request                          |            ");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [4]" + BLUE + " Delete Request                                  |            ");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [5]" + BLUE + " Display Information                             |            ");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [6]" + BLUE + " Logout                                          |            ");
        System.out.println(BLACK + "                                                 |                  " + BLUE + " [7]" + BLUE + " Exit                                            |            ");
        System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");

        int choice = getChoiceInput(7, "Choice");

        return choice;
    }

    public int getDocumentRequestView(List<DocumentRequest> documentRequest) {
        int noRequest = 0;

        // 1. EXACT WIDTHS (Total: 112 chars including separators)
        int wId = 3;      // ID
        int wDoc = 25;    // Document Name
        int wPurp = 20;   // Purpose
        int wStat = 10;   // Status
        int wReqD = 12;   // Request Date
        int wRem = 15;    // Remarks
        int wProD = 12;   // Processed Date

        // 2. Build Border (Using your Java 8 compatible rChar)
        String line = "+" + rChar("-", wId + 2) + "+" + rChar("-", wDoc + 2) + "+" + rChar("-", wPurp + 2)
                + "+" + rChar("-", wStat + 2) + "+" + rChar("-", wReqD + 2) + "+" + rChar("-", wRem + 2)
                + "+" + rChar("-", wProD + 2) + "+";

        System.out.println("\n" + line);
        // Header
        System.out.printf("| %-" + wId + "s | %-" + wDoc + "s | %-" + wPurp + "s | %-" + wStat + "s | %-" + wReqD + "s | %-" + wRem + "s | %-" + wProD + "s |%n",
                "ID", "DOCUMENT", "PURPOSE", "STATUS", "REQUEST DATE", "REMARKS", "PROCESS DATE");
        System.out.println(line);

        for (DocumentRequest dr : documentRequest) {
            // Safety: Ensure no nulls and truncate data if it's somehow longer than the column
            String name = fmt(dr.getDocumentName(), wDoc);
            String purp = dr.getPurpose() != null ? dr.getPurpose() : "";
            String stat = fmt(dr.getStatus(), wStat);
            String reqD = fmt(dr.getRequestDate(), wReqD);
            String rem = dr.getRemarks() != null ? dr.getRemarks() : "";
            String proD = fmt(dr.getProcessedDate(), wProD);

            boolean firstLine = true;

            // The Wrap Loop
            while (purp.length() > 0 || rem.length() > 0 || firstLine) {
                // Cut pieces for wrapping
                String pPart = (purp.length() > wPurp) ? purp.substring(0, wPurp) : purp;
                purp = (purp.length() > wPurp) ? purp.substring(wPurp) : "";

                String rPart = (rem.length() > wRem) ? rem.substring(0, wRem) : rem;
                rem = (rem.length() > wRem) ? rem.substring(wRem) : "";

                if (firstLine) {
                    System.out.printf("| %-" + wId + "d | %-" + wDoc + "s | %-" + wPurp + "s | %-" + wStat + "s | %-" + wReqD + "s | %-" + wRem + "s | %-" + wProD + "s |%n",
                            dr.getId(), name, pPart, stat, reqD, rPart, proD);
                    firstLine = false;
                } else {
                    // Secondary lines leave most columns blank
                    System.out.printf("| %-" + wId + "s | %-" + wDoc + "s | %-" + wPurp + "s | %-" + wStat + "s | %-" + wReqD + "s | %-" + wRem + "s | %-" + wProD + "s |%n",
                            "", "", pPart, "", "", rPart, "");
                }
            }
            System.out.println(line);
            noRequest++;
        }
        return noRequest;
    }

    public static String fmt(String text, int len) {
        if (text == null) {
            return "";
        }
        if (text.length() <= len) {
            return text;
        }
        return text.substring(0, len); // Hard truncate if it's too long
    }

    public static String rChar(String c, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public int getUserDetails(List<User> users) {
        int noRequest = 0;
        int w1 = 30; // Name
        int w2 = 15; // Contact
        int w3 = 25; // Address

        String separator = "+" + repeatChar('-', w1 + 2) + "+" + repeatChar('-', w2 + 2) + "+" + repeatChar('-', w3 + 2) + "+";
        String headerRow = String.format("| %-" + w1 + "s | %-" + w2 + "s | %-" + w3 + "s |", "NAME", "CONTACT NO.", "ADDRESS");

        System.out.println("\n                                                     PROFILE INFORMATION");
        System.out.println(separator);
        System.out.println(headerRow);
        System.out.println(separator);

        for (User user : users) {
            System.out.printf("| %-" + w1 + "s | %-" + w2 + "s | %-" + w3 + "s |%n",
                    user.getFullName(),
                    user.getContactNumber(),
                    user.getAddress());
        }

        System.out.println(separator);

        return noRequest;
    }

    public boolean viewDocumentRequest(int userId) {
        boolean is_successRequest = false;
        int noRequest = 0;

        System.out.println(BLUE + "\n                                                     VIEW DOCUMENT REQUEST      ");
        noRequest = getDocumentRequestView(documentRequest.fetchDocumentRequestById(userId));

        if (noRequest != 0) {
            is_successRequest = true;
        } else {
            System.out.println(RED + "                                                     No Results Found!");
        }

        return is_successRequest;

    }

    public boolean cancelDocumentRequest(int userId) {
        boolean is_successRequest = false;
        int noRequest = 0;

        System.out.println(BLUE + "\n                                                     CANCEL PENDING REQUEST (CHOOSE DOCUMENT REQUEST)       ");
        noRequest = getDocumentRequestView(documentRequest.fetchDocumentRequestByIdPending(userId));

        if (noRequest != 0) {
            int documentRequestId = 0;
            boolean is_validDocId = false;
            System.out.println(BLACK + "                                                     Enter [0] to go back.");
            do {
                documentRequestId = getChoiceInputDocID(0, "Document Request ID");
                if (documentRequestId == 0) {
                    System.out.println(RED + "                                                     Returning to Main Menu.");
                    return false;
                }
                is_validDocId = documentRequest.getCheckDocumentRequestPendingForCancelById(documentRequestId, userId);
                if (!is_validDocId) {
                    System.out.println(RED + "                                                     Please select a valid document request id!");
                }

            } while (!is_validDocId);

            is_successRequest = getConfirmation(documentRequestId, "", 2);
        } else {
            System.out.println(RED + "                                                     No Results Found!");
        }

        return is_successRequest;

    }

    public boolean deleteDocumentRequest(int userId) {
        boolean is_successRequest = false;
        int noRequest = 0;

        System.out.println(BLUE + "\n                                                     DELETE DOCUMENT REQUEST (CHOOSE DOCUMENT REQUEST)       ");
        noRequest = getDocumentRequestView(documentRequest.fetchDocumentRequestByIdPending(userId));
        if (noRequest != 0) {
            int documentRequestId = 0;
            boolean is_validDocId = false;
            System.out.println(BLACK + "                                                     Enter [0] to go back.");
            do {
                documentRequestId = getChoiceInputDocID(0, "Document Request ID");
                if (documentRequestId == 0) {
                    System.out.println(RED + "                                                     Returning to Main Menu.");
                    return false;
                }
                is_validDocId = documentRequest.getCheckDocumentRequestPendingForCancelById(documentRequestId, userId);
                if (!is_validDocId) {
                    System.out.println(RED + "                                                     Please select a valid document request id!");
                }

            } while (!is_validDocId);

            is_successRequest = getConfirmation(documentRequestId, "", 3);

        } else {
            System.out.println(RED + "                                                     No Results Found!");
        }

        return is_successRequest;
    }

    public boolean createDocumentRequestView(int userId) {
        int documentId = 0;
        int documentNo;
        String purpose = "";
        boolean is_successRequest = false;
        loginUserId = userId;

        System.out.println(BLUE + "\n                                                     CREATE DOCUMENT REQUEST (CHOOSE DOCUMENT TYPE)       ");

        DocTypeRepoImpl documentRepo = new DocTypeRepoImpl(dbConnect);
        List<DocumentType> documentType = documentRepo.getDocTypes();
        documentNo = displayDocumentType(documentType);

        boolean is_validDocId = false;
        System.out.println(BLACK + "                                                     Enter [0] to go back.");
        do {
            documentId = getChoiceInputDocID(documentNo, "Document ID");
            if (documentId == 0) {
                System.out.println(RED + "                                                     Returning to Main Menu.");
                return false;
            }
            is_validDocId = documentRequest.getCheckDocTypeById(documentId);
            if (!is_validDocId) {
                System.out.println(RED + "                                                     Please select a valid document id!");
            }

        } while (!is_validDocId);

        System.out.print(BLACK + "                                                     Enter your purpose: ");
        purpose = scanner.nextLine();

        is_successRequest = getConfirmation(documentId, purpose, 1);

        return is_successRequest;

    }

    public boolean getConfirmation(int id, String purpose, int menuId) {

        boolean is_validInput = false;
        boolean is_successRequest = false;
        String choice;
        do {
            try {
                System.out.print("                                                     Save Request? [Y] Yes or [N] No: ");
                choice = scanner.nextLine();
                if (String.valueOf(choice.charAt(0)).toLowerCase().equals("y")) {
                    if (menuId == 1) {
                        addDocumentRequest(id, purpose, loginUserId);
                    } else if (menuId == 2) {
                        cancelRequest(id);
                        System.out.println(GREEN + "                                                     You have successfuly cancelled your document request!");
                    } else if (menuId == 3) {
                        deleteRequest(id);
                        System.out.println(GREEN + "                                                     You have successfuly deleted your document request!");
                    }

                    is_validInput = true;
                    is_successRequest = true;
                } else if (String.valueOf(choice.charAt(0)).toLowerCase().equals("n")) {
                    System.out.println(RED + "                                                     Returning to Main Menu");
                    //displayUserView(loginFullName);
                    is_validInput = true;
                    is_successRequest = false;
                } else {
                    showInvalidInput();
                }

            } catch (StringIndexOutOfBoundsException e) {
                showInvalidInput();
            }
        } while (!is_validInput);

        return is_successRequest;

    }

    public static void addDocumentRequest(int documentId, String purpose, int userId) {

        DocumentRequest documentRequest = new DocumentRequest();
        DocumentRequestRepoImpl documentRequestRepo = new DocumentRequestRepoImpl(dbConnect);
        documentRequest.setDocument_id(documentId);
        documentRequest.setUser_id(userId);
        documentRequest.setPurpose(purpose);
        if (documentRequestRepo.createDocumentRequest(documentRequest)) {
            System.out.println(GREEN + "                                                     You have successfully saved your request!");
        } else {
            System.out.println(RED + "                                                     Failed request!");
        }
    }

    public boolean cancelRequest(int documentRequestId) {
        return documentRequest.cancelDocumentRequest(documentRequestId);
    }

    public boolean deleteRequest(int documentRequestId) {
        documentRequest.deleteDocumentRequestStatus(documentRequestId);
        return documentRequest.deleteDocumentRequest(documentRequestId);
    }

    public int getChoiceInputDocID(int numberLimit, String label) {
        int choice = 0;
        boolean is_validInput = false;
        do {
            try {
                System.out.print(BLACK + "                                                     Enter " + label + ": ");
                // If input is not an integer, throws InputMismatchException
                choice = scanner.nextInt();
                consumeBuffer();
                is_validInput = true;

            } catch (InputMismatchException e) {
                showInvalidInput();
                scanner.nextLine(); // Clear the invalid input from scanner buffer
            }
        } while (!is_validInput);

        return choice;
    }

    public int getChoiceInput(int numberLimit, String label) {
        int choice = 0;
        boolean is_validInput = false;
        do {
            try {
                System.out.print(BLACK + "                                                     Enter " + label + ": ");
                // If input is not an integer, throws InputMismatchException
                choice = scanner.nextInt();
                consumeBuffer();
                if (choice >= 1 && choice <= numberLimit) {
                    is_validInput = true;
                } else {
                    System.out.println(RED + "                                                     Invalid Input! Please select a valid ID from the list!");
                }
            } catch (InputMismatchException e) {
                showInvalidInput();
                scanner.nextLine(); // Clear the invalid input from scanner buffer
            }
        } while (!is_validInput);

        return choice;
    }

    public int displayDocumentType(List<DocumentType> documentTypes) {
        System.out.println("\n                                                     --- Document Types List ---");
        System.out.printf("                                                     %-5s | %-30s | %-10s | %-10s%n", "ID", "Document Name", "Fee", "Status");
        System.out.println("                                                     ----------------------------------------------------------------------");
        int i = 1;
        for (DocumentType dt : documentTypes) {
            String status = dt.isArchived() ? "Archived" : "Active";
            System.out.printf("                                                     %-5d | %-30s | %-10.2f | %-10s%n",
                    dt.getId(), dt.getDocumentName(), dt.getFee(), status);
            i++;
        }
        System.out.println("                                                     ----------------------------------------------------------------------\n");

        return i;

    }

    private static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();

        int i = 0;
        while (i < text.length()) {
            lines.add(text.substring(i, Math.min(i + width, text.length())));
            i += width;
        }

        return lines;
    }

    private static String getLine(List<String> lines, int index) {
        return (index < lines.size()) ? lines.get(index) : "";
    }

    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public void showLogoutMessage() {
        System.out.println(BLUE + "                                                     You have been logged out. Thank you!");
    }

    public void showSuccessMessage() {
        System.out.println("                                                     Success");
    }

    public void showErrorMessage() {
        System.out.println("                                                     Failed");
    }

    public void showInvalidInput() {
        System.out.println(RED + "                                                     Invalid Input. Please enter a valid choice!");
    }

    public void consumeBuffer() {
        scanner.nextLine();
    }

    public void systemExit() {
        System.out.println(BLUE + "                                                     Exiting..... Thank you!");
        System.exit(0);
    }

}