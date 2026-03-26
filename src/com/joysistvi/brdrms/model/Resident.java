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
public class Resident {

    private int residentId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;
    private String gender;
    private String civilStatus;
    private String contactNumber;
    private String email;
    private String address;
    private Timestamp dateRegistered;
   

    public Resident() {

    }

    public Resident(int residentId, String firstName, String lastName, String middleName, String birthDate,
            String gender, String civilStatus, String contactNumber, String email, String address) {
        this.residentId = residentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.civilStatus = civilStatus;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }

    public Resident(int residentId, String firstName, String lastName, String middleName, String birthDate,
            String gender, String civilStatus, String contactNumber, String email, String address, Timestamp dateRegistered) {
        this.residentId = residentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.civilStatus = civilStatus;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.dateRegistered = dateRegistered;
       
    }
    


    public void setId(int residentId) {
        this.residentId = residentId;

    }

    public int getId() {
        return residentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public Timestamp getDateRegistered(){
        return dateRegistered;
    }
    
    public void setDateRegistered(Timestamp dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

}
