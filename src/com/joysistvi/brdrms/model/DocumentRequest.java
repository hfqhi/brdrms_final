/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.model;

/**
 *
 * @author bc.macdon
 */
public class DocumentRequest {
    
    private int id;
    private int user_id;
    private int document_id;
    private String purpose;
    private String status;
    private String requestDate;
    private String documentName;
    private String remarks;
    private String processedDate;
    private String fullname;
    private double amount;
    private String receiptNo;
    
    public DocumentRequest(){
    
    }
    
    public DocumentRequest(int id, String purpose, String status, String documentName) {
        this.id = id;
        this.purpose = purpose;
        this.status = status;
        this.documentName = documentName;
    }

    public double getAmount() {
        return amount;
    }

    public String getReceiptNo() {
        return receiptNo;
    }
    
    public DocumentRequest(int id, String receiptNo, String fullname, String documentName, String processedDate, double amount) {
        this.id = id;
        this.receiptNo = receiptNo;
        this.fullname = fullname;
        this.documentName = documentName;
        this.processedDate = processedDate;
        this.amount = amount;
    }
    
    public DocumentRequest(int id, String purpose, String status, String documentName, String requestDate, String remarks, String processedDate, String fullname) {
        this.id = id;
        this.purpose = purpose;
        this.status = status;
        this.documentName = documentName;
        this.requestDate = requestDate;
        this.remarks = remarks;
        this.processedDate = processedDate;
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }
    
    public DocumentRequest(int id, String purpose, String status, String documentName, String requestDate, String remarks, String processedDate) {
        this.id = id;
        this.purpose = purpose;
        this.status = status;
        this.documentName = documentName;
        this.requestDate = requestDate;
        this.remarks = remarks;
        this.processedDate = processedDate;
    }

    public DocumentRequest(int id, int user_id, int document_id, String purpose, String status, String requestDate) {
        this.id = id;
        this.user_id = user_id;
        this.document_id = document_id;
        this.purpose = purpose;
        this.status = status;
        this.requestDate = requestDate;
    }

    public int getId() {
        return id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
    
    
    
    
}
