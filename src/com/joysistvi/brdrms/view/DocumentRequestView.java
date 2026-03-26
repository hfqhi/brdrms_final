/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.model.DocumentRequest;
import com.joysistvi.brdrms.impl.DocumentRequestRepoImpl;
import com.joysistvi.brdrms.impl.ResidentRepoImpl;
import com.joysistvi.brdrms.util.DbConnection;
import static com.joysistvi.brdrms.view.AdminView.BLACK;
import static com.joysistvi.brdrms.view.AdminView.CYAN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bejie
 */
public class DocumentRequestView {

    private final Scanner scanner;
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    private static DbConnection dbConnect = new DbConnection();
    DocumentRequestRepoImpl documentRequestRepo = new DocumentRequestRepoImpl(dbConnect);

    private int adminId;

    public DocumentRequestView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void manageDocumentRequestMenu(int adminId) {
        

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println(CYAN + "                                                                             MANAGE DOCUMENT REQUEST   ");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [1]"+ BLUE + " View All Request                                     | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [2]"+ BLUE + " Approve/Reject Document Request                      | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [3]"+ BLUE + " Delete Request                                       | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [4]"+ BLUE + " View Payment History                                 | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [5]"+ BLUE + " Search                                               | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [0]"+ BLUE + " Back                                                 | ");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");

            
            System.out.print(BLACK + "                                                    Enter your Choice: ");
            try {
                int choice = scanner.nextInt();
                consumeBuffer();
                switch (choice) {
                    case 1:
                        getDocumentRequestAdminView(documentRequestRepo.fetchDocumentRequestByAdmin());
                        break;
                    case 2:
                        approveRejectDocumentRequest(adminId);
                        break;
                    case 3:
                        deleteDocumentRequest(adminId);
                        break;
                    case 4:
                        getDocumentRequestPaymentAdminView(documentRequestRepo.fetchDocumentRequestPaymentHistoryByAdmin());
                        break;
                    case 5:
                        System.out.print(BLUE + "	\t\t\t\t\t\t\t\t Enter keywords: ");
                        
                        getDocumentRequestAdminView(documentRequestRepo.fetchDocumentRequestByAdminSearch(scanner.nextLine()));
                        break;
                    case 0:
                        System.out.println(BLUE + "	\t\t\t\t\t\t\t\t Back to Main Menu...");
                        isRunning = false;
                        break;
                        
                    default:
                        System.out.println(RED + "	\t\t\t\t\t\t\t\t Invalid choice.");
                        break;

                }

            } catch (NumberFormatException e) {
                System.out.println(RED + "	\t\t\t\t\t\t\t\t Invalid input. Please enter a valid number.");
            } catch (InputMismatchException e) {
                System.out.println(RED + "	\t\t\t\t\t\t\t\t\t No Letters Please");
                consumeBuffer();
            }

        }
    }

    public boolean approveRejectDocumentRequest(int adminId) {
        int noRequest = 0;
        boolean is_successRequest = false;
        System.out.println(BLUE + "\t\t\t\t\t\t\t      APPROVE/REJECT REQUEST (CHOOSE DOCUMENT REQUEST)       ");

        noRequest = getDocumentRequestAdminView(documentRequestRepo.fetchDocumentRequestPendingByAdmin());

        if (noRequest != 0) {
            int documentRequestId = 0;
            boolean is_validDocId = false;
            do {
                documentRequestId = getChoiceInputDocID(0, "Document Request ID");
                is_validDocId = documentRequestRepo.getCheckDocumentRequestPendingForById(documentRequestId);
                if (is_validDocId) {
                    is_validDocId = true;
                } else {
                    System.out.println(RED + "	\t\t\t\t\t\t\t\t Please select a valid document request id!");
                }

            } while (!is_validDocId);

            is_successRequest = getConfirmationApproval(documentRequestId, adminId);
        } else {
            System.out.println(RED + "	\t\t\t\t\t\t\t\t\tNo Results Found!");
        }

        return is_successRequest;
    }
    
    public boolean deleteDocumentRequest(int adminId) {
        int noRequest = 0;
        boolean is_successRequest = false;
        System.out.println(BLUE + "	\t\t\t\t\t\t\t\t    DELETE REQUEST (CHOOSE DOCUMENT REQUEST)       ");

        noRequest = getDocumentRequestAdminView(documentRequestRepo.fetchDocumentRequestNotApprovedByAdmin());

        if (noRequest != 0) {
            int documentRequestId = 0;
            boolean is_validDocId = false;
            do {
                documentRequestId = getChoiceInputDocID(0, "Document Request ID");
                is_validDocId = documentRequestRepo.getCheckDocumentRequestNotApprovedForById(documentRequestId);
                if (is_validDocId) {
                    is_validDocId = true;
                } else {
                    System.out.println(RED + "	\t\t\t\t\t\t\t\t  Please select a valid document request id!");
                }

            } while (!is_validDocId);

            is_successRequest = getConfirmation(documentRequestId,adminId, "",3);
        } else {
            System.out.println(RED + "	\t\t\t\t\t\t\t\t\t  No Results Found!");
        }

        return is_successRequest;
    }
    
    public boolean getConfirmationApproval(int docRequestId, int adminId) {

        boolean is_validInput = false;
        boolean is_successRequest = false;
        String choice;
        double amount;
        String receiptNo;
        String remarks;
        do {
            try {
                System.out.print(BLUE + "	\t\t\t\t\t\t\t Approve Request? [A] Approve or [R] Reject: " + RESET);
                choice = scanner.nextLine();
                if (String.valueOf(choice.charAt(0)).toLowerCase().equals("a")) {
                    
                    System.out.print("	\t\t\t\t\t\t\t\t\t\t Remarks: ");
                    remarks = scanner.nextLine();
                    System.out.print("	\t\t\t\t\t\t\t\t\t\t Amount: ");
                    amount = scanner.nextDouble();
                    consumeBuffer();
                    System.out.print("	\t\t\t\t\t\t\t\t\t\t Official Receipt No.: ");
                    receiptNo = scanner.nextLine();
                    getConfirmationToApprove(docRequestId,adminId,remarks,amount,receiptNo);
                    
                    is_validInput = true;
                    is_successRequest = true;
                } else if (String.valueOf(choice.charAt(0)).toLowerCase().equals("r")) {
                    
                    System.out.print("	\t\t\t\t\t\t\t\t\t\t Remarks: ");
                    remarks = scanner.nextLine();
                    
                    getConfirmation(docRequestId,adminId,remarks,2);
                    is_validInput = true;
                    is_successRequest = true;
                } else {
                    showInvalidInput();
                }

            } catch (InputMismatchException e) {
                showInvalidInput();
                scanner.nextLine(); // Clear the invalid input from scanner buffer
                return false;
            }
        } while (!is_validInput);

        return is_successRequest;

    }
    
    public boolean getConfirmation(int id, int adminId, String remarks, int menuId) {

        boolean is_validInput = false;
        boolean is_successRequest = false;
        String choice;
        do {
            try {
                System.out.print(BLUE + "	\t\t\t\t\t\t\t\t Save Request? [Y] Yes or [N] No: " + RESET);
                choice = scanner.nextLine();
                if (String.valueOf(choice.charAt(0)).toLowerCase().equals("y")) {
                    if (menuId == 1) {
                        //addDocumentRequest(id, purpose, loginUserId);
                    } else if (menuId == 2) {
                        rejectedRequest(id,adminId,remarks);
                        System.out.println(GREEN + "	\t\t\t\t\t\t\t\t You have successfuly reject the document request "+id+"!");
                    } else if (menuId == 3) {
                        deleteRequest(id);
                        System.out.println(GREEN + "	\t\t\t\t\t\t\t\t You have successfuly deleted your document request!");
                    }

                    is_validInput = true;
                    is_successRequest = true;
                } else if (String.valueOf(choice.charAt(0)).toLowerCase().equals("n")) {
                    System.out.println(BLUE + "	\t\t\t\t\t\t\t\t\t\t Returning to Main Menu");
                    //displayUserView(loginFullName);
                    is_validInput = true;
                    is_successRequest = false;
                } else {
                    showInvalidInput();
                }

            } catch (InputMismatchException e) {
                showInvalidInput();
                scanner.nextLine(); // Clear the invalid input from scanner buffer
                return false;
            }
        } while (!is_validInput);

        return is_successRequest;

    }
    
    public boolean getConfirmationToApprove(int docRequestId, int adminId, String remarks, double amount, String receiptNo) {

        boolean is_validInput = false;
        boolean is_successRequest = false;
        String choice;
        do {
            try {
                System.out.print(BLUE + "	\t\t\t\t\t\t\t\t Save Request? [Y] Yes or [N] No: " + RESET);
                choice = scanner.nextLine();
                if (String.valueOf(choice.charAt(0)).toLowerCase().equals("y")) {
                    documentRequestRepo.approveDocumentRequestInsertStatus(docRequestId, adminId, remarks);
                    documentRequestRepo.approveDocumentRequestInsertPayment(docRequestId, adminId, amount, receiptNo);
                    documentRequestRepo.approveDocumentRequest(docRequestId, adminId);
                     System.out.println(GREEN + "	\t\t\t\t\t\t\t\t You have successfuly approved document request "+docRequestId+"!");

                    
                    
                    is_validInput = true;
                    is_successRequest = true;
                } else if (String.valueOf(choice.charAt(0)).toLowerCase().equals("n")) {
                    System.out.println(RED + "	\t\t\t\t\t\t\t\t\t Returning to Main Menu");
                    //displayUserView(loginFullName);
                    is_validInput = true;
                    is_successRequest = false;
                } else {
                    showInvalidInput();
                }

            } catch (InputMismatchException e) {
                showInvalidInput();
                scanner.nextLine(); // Clear the invalid input from scanner buffer
                return false;
            }
        } while (!is_validInput);

        return is_successRequest;

    }
    
    
    public boolean deleteRequest(int documentRequestId) {
        documentRequestRepo.deleteDocumentRequestStatus(documentRequestId);
        return documentRequestRepo.deleteDocumentRequest(documentRequestId);
    }
    
    public boolean rejectedRequest(int docRequestId, int adminId, String remarks){
        documentRequestRepo.rejectedDocumentRequestInsertStatus(docRequestId, adminId, remarks);
        return documentRequestRepo.rejectedDocumentRequest(docRequestId, adminId);
        
    }
    

    public int getChoiceInputDocID(int numberLimit, String label) {
        int choice = 0;
        boolean is_validInput = false;
        do {
            try {
                System.out.print(BLACK + "	\t\t\t\t\t\t\t\t\t\t  Enter " + label + ": ");
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
    
    public int getDocumentRequestPaymentAdminView(List<DocumentRequest> documentRequest) {
        int noRequest = 0;

        int wId = 3, wReceipt = 15, wName = 20, wDoc = 20, wDate = 15, wAmount = 10;

        // Top Border
        System.out.println("\n                         +-----+-----------------+----------------------+----------------------+-----------------+------------+");

        // Header
        System.out.printf("                         | %-3s | %-15s | %-20s | %-20s | %-15s | %-10s |%n",
                "ID", "Receipt No", "Full Name", "Document", "Payment Date", "Amount");

        // Separator
        System.out.println("                         +-----+-----------------+----------------------+----------------------+-----------------+------------+");

        for (DocumentRequest dr : documentRequest) {

            String receiptNo = dr.getReceiptNo();   // make sure getter exists
            String fullname = dr.getFullname();
            String documentName = dr.getDocumentName();
            String dateProcessed = String.valueOf(dr.getProcessedDate());
            String amount = "Php "+String.format("%.2f", dr.getAmount());

            List<String> receiptLines = wrapText(receiptNo, wReceipt);
            List<String> nameLines = wrapText(fullname, wName);
            List<String> docLines = wrapText(documentName, wDoc);
            List<String> dateLines = wrapText(dateProcessed, wDate);
            List<String> amountLines = wrapText(amount, wAmount);

            int maxLines = Collections.max(Arrays.asList(
                    receiptLines.size(),
                    nameLines.size(),
                    docLines.size(),
                    dateLines.size(),
                    amountLines.size()
            ));

            for (int i = 0; i < maxLines; i++) {
                System.out.printf("                         | %-3s | %-15s | %-20s | %-20s | %-15s | %10s |%n",
                        (i == 0 ? dr.getId() : ""),
                        (i < receiptLines.size() ? receiptLines.get(i) : ""),
                        (i < nameLines.size() ? nameLines.get(i) : ""),
                        (i < docLines.size() ? docLines.get(i) : ""),
                        (i < dateLines.size() ? dateLines.get(i) : ""),
                        (i < amountLines.size() ? amountLines.get(i) : "")
                );
            }

            // Row separator
            System.out.println("                         +-----+-----------------+----------------------+----------------------+-----------------+------------+");
            noRequest++;
        }

        return noRequest;
    }

    public int getDocumentRequestAdminView(List<DocumentRequest> documentRequest) {
        int noRequest = 0;
        int wId = 3, wName = 20, wDoc = 20, wPurpose = 20, wStatus = 10, wReqDate = 15, wRemarks = 20, wProcDate = 15;

        // Top Border
        System.out.println("\n               +-----+----------------------+----------------------+----------------------+------------+-----------------+----------------------+-----------------+");

        // Header
        System.out.printf("               | %-3s | %-20s | %-20s | %-20s | %-10s | %-15s | %-20s | %-15s |%n",
                "ID", "Full Name", "Document", "Purpose", "Status", "Request Date", "Remarks", "Process Date");

        // Separator
        System.out.println("               +-----+----------------------+----------------------+----------------------+------------+-----------------+----------------------+-----------------+");

        for (DocumentRequest dr : documentRequest) {

            List<String> nameLines = wrapText(dr.getFullname(), wName);
            List<String> docLines = wrapText(dr.getDocumentName(), wDoc);
            List<String> purposeLines = wrapText(dr.getPurpose(), wPurpose);
            List<String> statusLines = wrapText(dr.getStatus(), wStatus);
            List<String> reqDateLines = wrapText(String.valueOf(dr.getRequestDate()), wReqDate);
            List<String> remarksLines = wrapText(dr.getRemarks(), wRemarks);
            List<String> procDateLines = wrapText(dr.getProcessedDate(), wProcDate);

            int maxLines = Collections.max(Arrays.asList(
                    nameLines.size(),
                    docLines.size(),
                    purposeLines.size(),
                    statusLines.size(),
                    reqDateLines.size(),
                    remarksLines.size(),
                    procDateLines.size()
            ));

            for (int i = 0; i < maxLines; i++) {
                System.out.printf("               | %-3s | %-20s | %-20s | %-20s | %-10s | %-15s | %-20s | %-15s |%n",
                        (i == 0 ? dr.getId() : ""),
                        (i < nameLines.size() ? nameLines.get(i) : ""),
                        (i < docLines.size() ? docLines.get(i) : ""),
                        (i < purposeLines.size() ? purposeLines.get(i) : ""),
                        (i < statusLines.size() ? statusLines.get(i) : ""),
                        (i < reqDateLines.size() ? reqDateLines.get(i) : ""),
                        (i < remarksLines.size() ? remarksLines.get(i) : ""),
                        (i < procDateLines.size() ? procDateLines.get(i) : "")
                );
            }

            // Row separator
            System.out.println("               +-----+----------------------+----------------------+----------------------+------------+-----------------+----------------------+-----------------+");
            noRequest++;
        }

        return noRequest;
    }

    public static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();

        if (text == null) {
            text = "";
        }

        while (text.length() > width) {
            lines.add(text.substring(0, width));
            text = text.substring(width);
        }
        lines.add(text);

        return lines;
    }

    public void showInvalidInput() {
        System.out.println(RED + "	\t\t\t\t\t\t\t\t Invalid Input. Please enter a valid choice!");
    }

    public void consumeBuffer() {
        scanner.nextLine();
    }

}
