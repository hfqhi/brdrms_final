package com.joysistvi.brdrms.view;

import com.joysistvi.brdrms.model.DocumentType;
import com.joysistvi.brdrms.impl.DocTypeRepoImpl;
import java.util.List;
import java.util.Scanner;

public class DocTypeView {

    private final Scanner scanner;

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    
    public DocTypeView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void manageDocTypeMenu(DocTypeRepoImpl docTypeRepo) {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println( CYAN + "                                                                             MANAGE DOCUMENT TYPE   " + RESET);
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [1]" + BLUE + " Add Document Type                                    | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [2]" + BLUE + " View All Document Types                              | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [3]" + BLUE + " View Active Document Types                           | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [4]" + BLUE + " Update Document Type                                 | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [5]" + BLUE + " Archive Document Type                                | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [6]" + BLUE + " Restore Document Type                                | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [7]" + BLUE + " Delete Document Type                                 | ");
            System.out.println(BLACK +"                                                 |                  "+ CYAN +" [0]" + BLUE + " Back to Admin Menu                                   | ");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣\n");
            System.out.print("                                                    Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("\t\t\t\t\t\t\t\t Enter Document Name: ");
                        String name = scanner.nextLine();
                        System.out.print("	\t\t\t\t\t\t\t\t Enter Fee:           ");
                        double fee = Double.parseDouble(scanner.nextLine());

                        // Using the new DocType constructor (name, fee)
                        DocumentType newDt = new DocumentType(name, fee);
                        if (docTypeRepo.createDocType(newDt)) {
                            showSuccessMessage();
                        } else {
                            showErrorMessage();
                        }
                        break;
                    case 2:
                        displayDocTypes(docTypeRepo.getDocTypes());
                        break;
                    case 3:
                        displayDocTypes(docTypeRepo.getActiveDocTypes());
                        break;
                    case 4:
                        System.out.print("\t\t\t\t\t\t\t\t Enter DocType ID to update: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        DocumentType existing = docTypeRepo.getDocTypeById(updateId);

                        if (existing != null) {
                            System.out.print("	\t\t\t\t\t\t\t\t Enter new Document Name (" + existing.getDocumentName() + "): ");
                            String uName = scanner.nextLine();
                            System.out.print("	\t\t\t\t\t\t\t\t Enter new Fee (" + existing.getFee() + "): ");
                            String uFeeStr = scanner.nextLine();

                            if (!uName.trim().isEmpty()) {
                                existing.setDocumentName(uName);
                            }
                            if (!uFeeStr.trim().isEmpty()) {
                                existing.setFee(Double.parseDouble(uFeeStr));
                            }

                            if (docTypeRepo.updateDocType(existing)) {
                                showSuccessMessage();
                            } else {
                                showErrorMessage();
                            }
                        } else {
                            System.out.println(RED + "	\t\t\t\t\t\t\t\t\t Document Type not found!");
                        }
                        break;
                        
                    case 5:
                        System.out.print("\t\t\t\t\t\t\t\t\t Enter DocType ID to archive: ");
                        int archId = Integer.parseInt(scanner.nextLine());
                        if (docTypeRepo.archiveDocType(archId)) {
                            showSuccessMessage();
                        } else {
                            showErrorMessage();
                        }
                        break;
                    case 6:
                        System.out.print("\t\t\t\t\t\t\t\t\t Enter DocType ID to restore: ");
                        int restId = Integer.parseInt(scanner.nextLine());
                        if (docTypeRepo.restoreDocType(restId)) {
                            showSuccessMessage();
                        } else {
                            showErrorMessage();
                        }
                        break;
                    case 7:
                        System.out.print("\t\t\t\t\t\t\t\t\t Enter DocType ID to delete: ");
                        int delId = Integer.parseInt(scanner.nextLine());
                        if (docTypeRepo.deleteDocType(delId)) {
                            showSuccessMessage();
                        } else {
                            showErrorMessage();
                        }
                        break;
                    case 0:
                        System.out.println(BLUE + "	\t\t\t\t\t\t\t\t\t   Returning to Admin Menu...");
                        System.out.println("\n\n\n");
                        break;
                    default:
                        System.out.println(RED + "	\t\t\t\t\t\t\t\t\t   Invalid choice.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println(RED +"	\t\t\t\t\t\t\t\tInvalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println(RED + "	\t\t\t\t\t\t\t\t An error occurred: " + e.getMessage());
            }
        }
    }

    private void displayDocTypes(List<DocumentType> list) {
        System.out.println(BLUE + "\n                                                        --- Document Types List ---");
        System.out.printf("                                          %-5s | %-30s | %-10s | %-10s%n", "ID", "Document Name", "Fee", "Status");
        System.out.println("                                        ----------------------------------------------------------------------");
        for (DocumentType dt : list) {
            String status = dt.isArchived() ? "Archived" : "Active";
            System.out.printf("                                         %-5d | %-30s | %-10.2f | %-10s%n",
                    dt.getId(), dt.getDocumentName(), dt.getFee(), status);
        }
        System.out.println("                                        ----------------------------------------------------------------------\n");
    }

    public void showSuccessMessage() {
        System.out.println(GREEN + "	\t\t\t\t\t\t\t\t\t\t Success!");
    }

    public void showErrorMessage() {
        System.out.println(RED + "	\t\t\t\t\t\t\t\t\t\t Failed!");
    }
}
