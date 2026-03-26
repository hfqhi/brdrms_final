package com.joysistvi.brdrms.model;

import java.sql.Timestamp;

public class DocumentType {

    private int id;
    private String documentName;
    private String description;
    private double fee;
    private boolean archived;
    private Timestamp updatedAt;

    public DocumentType() {
    }
    
     public DocumentType(String documentName, double fee) {
        this.documentName = documentName;
        this.fee = fee;
        this.archived = false;
    }
    

    public DocumentType(int id, String documentName,double fee, boolean archived, Timestamp updatedAt) {
        this.id = id;
        this.documentName = documentName;
        this.fee = fee;
        this.archived = archived;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
