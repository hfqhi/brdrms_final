/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.impl;

import com.joysistvi.brdrms.repo.ResidentRepo;
import com.joysistvi.brdrms.model.Resident;
import com.joysistvi.brdrms.util.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Hacutina
 */
public class ResidentRepoImpl implements ResidentRepo {

    private final DbConnection dbConnection;
    public ResidentRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Resident> getResident() {
        String query = "SELECT * FROM tbl_residents WHERE is_archived = 0";

        List<Resident> Residents = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            while (result.next()) {
                int id = result.getInt("resident_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String middleName = result.getString("middle_name");
                String birthDate = result.getString("birthdate");
                String gender = result.getString("gender");
                String civilStatus = result.getString("civil_status");
                String contactNumber = result.getString("contact_number");
                String email = result.getString("email");
                String address = result.getString("address");
                Timestamp dateRegistered = result.getTimestamp("date_registered");

                Residents.add(new Resident(id, firstName, lastName, middleName, birthDate, gender, civilStatus, contactNumber, email, address, dateRegistered));
            }
        } catch (SQLException ex) {
            System.out.println("Get Resident: " + ex.getMessage());
        }
        return Residents;
    }
    @Override
    public List<Resident> getResidentArchived() {
        String query = "SELECT * FROM tbl_residents WHERE is_archived = 1";

        List<Resident> Residents = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            while (result.next()) {
                int id = result.getInt("resident_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String middleName = result.getString("middle_name");
                String birthDate = result.getString("birthdate");
                String gender = result.getString("gender");
                String civilStatus = result.getString("civil_status");
                String contactNumber = result.getString("contact_number");
                String email = result.getString("email");
                String address = result.getString("address");
                Timestamp dateRegistered = result.getTimestamp("date_registered");

                Residents.add(new Resident(id, firstName, lastName, middleName, birthDate, gender, civilStatus, contactNumber, email, address, dateRegistered));
            }
        } catch (SQLException ex) {
            System.out.println("Get Resident Archived: " + ex.getMessage());
        }
        return Residents;
    }

    @Override
    public boolean createResident(Resident resident) {
        String query = "INSERT INTO tbl_residents(first_name, last_name, middle_name, birthdate, gender, civil_status, contact_number, email, address)"
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setString(1, resident.getFirstName());
            psmt.setString(2, resident.getLastName());
            psmt.setString(3, resident.getMiddleName());
            psmt.setString(4, resident.getBirthDate());
            psmt.setString(5, resident.getGender());
            psmt.setString(6, resident.getCivilStatus());
            psmt.setString(7, resident.getContactNumber());
            psmt.setString(8, resident.getEmail());
            psmt.setString(9, resident.getAddress());

            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Create Resident: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Resident getResidentById(int id) {
        String query = "SELECT * FROM tbl_residents WHERE resident_id = ? AND is_archived = 0";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    Resident resident = new Resident();
                    resident.setId(result.getInt("resident_id"));
                    resident.setFirstName(result.getString("first_name"));
                    resident.setLastName(result.getString("last_name"));
                    resident.setMiddleName(result.getString("middle_name"));
                    resident.setBirthDate(result.getString("birthdate"));
                    resident.setGender(result.getString("gender"));
                    resident.setCivilStatus(result.getString("civil_status"));
                    resident.setContactNumber(result.getString("contact_number"));
                    resident.setEmail(result.getString("email"));
                    resident.setAddress(result.getString("address"));

                    return resident;
                }
            }

        } catch (Exception ex) {
            System.out.println("Fetch error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Resident getArchivedResidentById(int id) {
        String query = "SELECT * FROM tbl_residents WHERE resident_id = ? AND is_archived = 1";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    Resident resident = new Resident();
                    resident.setId(result.getInt("resident_id"));
                    resident.setFirstName(result.getString("first_name"));
                    resident.setLastName(result.getString("last_name"));
                    resident.setMiddleName(result.getString("middle_name"));
                    resident.setBirthDate(result.getString("birthdate"));
                    resident.setGender(result.getString("gender"));
                    resident.setCivilStatus(result.getString("civil_status"));
                    resident.setContactNumber(result.getString("contact_number"));
                    resident.setEmail(result.getString("email"));
                    resident.setAddress(result.getString("address"));

                    return resident;
                }
            }

        } catch (Exception ex) {
            System.out.println("Fetch error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateResidentInfo(Resident resident) {
        String query = "UPDATE tbl_residents SET first_name=?, last_name=?, middle_name=?, birthdate=?, gender=?, contact_number=?, email=?, address=? WHERE resident_id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setString(1, resident.getFirstName());
            psmt.setString(2, resident.getLastName());
            psmt.setString(3, resident.getMiddleName());
            psmt.setString(4, resident.getBirthDate());
            psmt.setString(5, resident.getGender());
            psmt.setString(6, resident.getContactNumber());
            psmt.setString(7, resident.getEmail());
            psmt.setString(8, resident.getAddress());
            psmt.setInt(9, resident.getId());

            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Update Resident: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteResident(int id) {
        String query = "DELETE FROM tbl_residents where resident_id = ?";
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Delete Resident: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean archiveResident(int id) {
        String query = "UPDATE tbl_residents SET is_archived=1 WHERE resident_id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);

            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Archive Resident: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean restoreResident(int id) {
        String query = "UPDATE tbl_residents SET is_archived=0 WHERE resident_id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, id);

            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Restore Resident: " + ex.getMessage());
        }
        return false;
    }
    
    @Override
    public List<Resident> searchResident(String searchKeyword) {
        String query = "SELECT * FROM tbl_residents WHERE (first_name LIKE ? OR last_name LIKE ? OR middle_name LIKE ? OR birthdate LIKE ? OR gender LIKE ? "
                + " OR civil_status LIKE ? OR contact_number LIKE ? OR email LIKE ? OR address LIKE ?) AND is_archived = 0 ";

        List<Resident> residents = new ArrayList<>();

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setString(1, "%" + searchKeyword + "%");
            psmt.setString(2, "%" + searchKeyword + "%");
            psmt.setString(3, "%" + searchKeyword + "%");
            psmt.setString(4, "%" + searchKeyword + "%");
            psmt.setString(5, "%" + searchKeyword + "%");
            psmt.setString(6, "%" + searchKeyword + "%");
            psmt.setString(7, "%" + searchKeyword + "%");
            psmt.setString(8, "%" + searchKeyword + "%");
            psmt.setString(9, "%" + searchKeyword + "%");

            try (ResultSet result = psmt.executeQuery()) {
            while (result.next()) {
                residents.add(new Resident(
                        result.getInt("resident_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("middle_name"),
                        result.getString("birthdate"),
                        result.getString("gender"),
                        result.getString("civil_status"),
                        result.getString("contact_number"),
                        result.getString("email"),
                        result.getString("address"),
                        result.getTimestamp("date_registered")
                ));
            }
            } // end try-with-resources ResultSet

        } catch (SQLException ex) {
            System.out.println("Search Resident: " + ex.getMessage());
        }

        return residents;
    }
}
