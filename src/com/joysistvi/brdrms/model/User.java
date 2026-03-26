/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.model;
import java.sql.Timestamp;

/**
 *
 * @author Hacutina
 */
public class User {
    
    private int Id;
    private String userName;
    private String password;
    private String fullName;
    private String contactNumber;
    private String address;
    private String role;
    private String status;
    private Timestamp dateStatus;
    
    
    
    public User (){
        
    }

    public User(int Id, String fullName, String contactNumber, String address) {
        this.Id = Id;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
    }
    
    
    public User(int Id, String userName, String password, String fullName, String contactNumber, String address) {
        this.Id = Id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
    }
    
    public User(int Id, String userName, String password, String fullName, String contactNumber, String address, String role) {
        this.Id = Id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.role = role;
    }
    
     public User(int Id, String userName, String password, String fullName, String contactNumber, String address, String role, Timestamp dateStatus) {
        this.Id = Id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.role = role;
        this.dateStatus = dateStatus;
    }
    
    public User(int Id, String userName, String password, String fullName, String contactNumber, String address, String role, String status, Timestamp dateStatus) {
        this.Id = Id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.role = role;
        this.status = status;
        this.dateStatus = dateStatus;
    }

    public void setId(int Id){
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(Timestamp dateStatus) {
        this.dateStatus = dateStatus;
    }


}
