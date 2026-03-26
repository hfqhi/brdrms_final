/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.impl;

import com.joysistvi.brdrms.repo.DocumentRequestRepo;
import com.joysistvi.brdrms.model.DocumentRequest;
import com.joysistvi.brdrms.util.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bc.macdon
 */
public class DocumentRequestRepoImpl implements DocumentRequestRepo {
    
    private final DbConnection dbConnection;

    public DocumentRequestRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<DocumentRequest> fetchDocumentRequestByAdmin() {
        String query = "SELECT a.id,a.user_id,a.purpose,a.status,date(a.request_date) as request_date, b.id as document_id,b.document_name,c.full_name, d.remarks,date(d.date_processed) as date_processed FROM tbl_document_request a "
                + "inner join tbl_document_type b on a.document_id = b.id "
                + "inner join tbl_users c on a.user_id = c.id left join tbl_document_request_status d on a.id = d.document_request_id order by a.id ";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String documentName = result.getString("document_name");
                    String purpose = result.getString("purpose");
                    String status = result.getString("status");
                    String dateRequest = result.getString("request_date");
                    String remarks = result.getString("remarks");
                    String dateProcessed = result.getString("date_processed");
                    String fullname = result.getString("full_name");

                    documentType.add(new DocumentRequest(id, purpose, status, documentName, dateRequest, remarks, dateProcessed, fullname));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Admin: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public List<DocumentRequest> fetchDocumentRequestByAdminSearch(String kwords) {
        String query = "SELECT a.id,a.user_id,a.purpose,a.status,date(a.request_date) as request_date, b.id as document_id,b.document_name,c.full_name, d.remarks,date(d.date_processed) as date_processed FROM tbl_document_request a "
                + "inner join tbl_document_type b on a.document_id = b.id "
                + "inner join tbl_users c on a.user_id = c.id left join tbl_document_request_status d on a.id = d.document_request_id "
                + "WHERE b.document_name like ? OR a.purpose like ? OR a.status like ? OR a.request_date like ? OR d.remarks like ? OR d.date_processed like ? OR c.full_name like ?  order by a.id";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            
            psmt.setString(1, "%"+kwords+"%");
            psmt.setString(2, "%"+kwords+"%");
            psmt.setString(3, "%"+kwords+"%");
            psmt.setString(4, "%"+kwords+"%");
            psmt.setString(5, "%"+kwords+"%");
            psmt.setString(6, "%"+kwords+"%");
            psmt.setString(7, "%"+kwords+"%");
            
            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String documentName = result.getString("document_name");
                    String purpose = result.getString("purpose");
                    String status = result.getString("status");
                    String dateRequest = result.getString("request_date");
                    String remarks = result.getString("remarks");
                    String dateProcessed = result.getString("date_processed");
                    String fullname = result.getString("full_name");

                    documentType.add(new DocumentRequest(id, purpose, status, documentName, dateRequest, remarks, dateProcessed, fullname));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Admin: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public List<DocumentRequest> fetchDocumentRequestPaymentHistoryByAdmin() {
        String query = "SELECT a.id, a.receipt_number, e.full_name, c.document_name, date(d.date_processed) as date_processed, a.amount  FROM tbl_document_request_payment a inner join tbl_document_request b on a.document_request_id = b.id \n" +
        "inner join tbl_document_type c on b.document_id = c.id inner join tbl_document_request_status d on b.id = d.document_request_id \n" +
        "inner join tbl_users e on b.user_id = e.id";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String receiptNo = result.getString("receipt_number");
                    String fullname = result.getString("full_name");
                    String documentName = result.getString("document_name");
                    String dateProcessed = result.getString("date_processed");
                    double amount = result.getDouble("amount");

                    documentType.add(new DocumentRequest(id, receiptNo, fullname, documentName, dateProcessed, amount));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Admin: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public List<DocumentRequest> fetchDocumentRequestPendingByAdmin() {
        String query = "SELECT a.id,a.user_id,a.purpose,a.status,date(a.request_date) as request_date, b.id as document_id,b.document_name,c.full_name, d.remarks,date(d.date_processed) as date_processed FROM tbl_document_request a "
                + "inner join tbl_document_type b on a.document_id = b.id "
                + "inner join tbl_users c on a.user_id = c.id left join tbl_document_request_status d on a.id = d.document_request_id where a.status = 'PENDING' order by a.id ";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String documentName = result.getString("document_name");
                    String purpose = result.getString("purpose");
                    String status = result.getString("status");
                    String dateRequest = result.getString("request_date");
                    String remarks = result.getString("remarks");
                    String dateProcessed = result.getString("date_processed");
                    String fullname = result.getString("full_name");

                    documentType.add(new DocumentRequest(id, purpose, status, documentName, dateRequest, remarks, dateProcessed, fullname));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Admin: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public List<DocumentRequest> fetchDocumentRequestNotApprovedByAdmin() {
        String query = "SELECT a.id,a.user_id,a.purpose,a.status,date(a.request_date) as request_date, b.id as document_id,b.document_name,c.full_name, d.remarks,date(d.date_processed) as date_processed FROM tbl_document_request a "
                + "inner join tbl_document_type b on a.document_id = b.id "
                + "inner join tbl_users c on a.user_id = c.id left join tbl_document_request_status d on a.id = d.document_request_id where a.status != 'APPROVED' order by a.id ";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String documentName = result.getString("document_name");
                    String purpose = result.getString("purpose");
                    String status = result.getString("status");
                    String dateRequest = result.getString("request_date");
                    String remarks = result.getString("remarks");
                    String dateProcessed = result.getString("date_processed");
                    String fullname = result.getString("full_name");

                    documentType.add(new DocumentRequest(id, purpose, status, documentName, dateRequest, remarks, dateProcessed, fullname));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Admin: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public List<DocumentRequest> fetchDocumentRequestById(int userId) {
        String query = "SELECT a.id,a.user_id,a.purpose,a.status,b.id as document_id,b.document_name, date(a.request_date) as request_date, c.remarks, date(c.date_processed) as date_processed FROM tbl_document_request a "
                + "inner join tbl_document_type b on a.document_id = b.id left join tbl_document_request_status c on a.id = c.document_request_id "
                + "where a.user_id = ? order by a.id";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, userId);
            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String documentName = result.getString("document_name");
                    String purpose = result.getString("purpose");
                    String status = result.getString("status");
                    String dateRequest = result.getString("request_date");
                    String remarks = result.getString("remarks");
                    String dateProcessed = result.getString("date_processed");

                    documentType.add(new DocumentRequest(id, purpose, status, documentName, dateRequest, remarks, dateProcessed));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get DocType By Id: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public List<DocumentRequest> fetchDocumentRequestByIdPending(int userId) {
        String query = "SELECT a.id,a.user_id,a.purpose,a.status,b.id as document_id,b.document_name, date(a.request_date) as request_date, c.remarks, date(c.date_processed) as date_processed FROM tbl_document_request a "
                + "inner join tbl_document_type b on a.document_id = b.id left join tbl_document_request_status c on a.id = c.document_request_id "
                + "where a.user_id = ? and a.status = 'PENDING' order by a.id";

        List<DocumentRequest> documentType = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, userId);
            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String documentName = result.getString("document_name");
                    String purpose = result.getString("purpose");
                    String status = result.getString("status");
                    String dateRequest = result.getString("request_date");
                    String remarks = result.getString("remarks");
                    String dateProcessed = result.getString("date_processed");
                    //double documentFee = result.getDouble("document_fee");

                    documentType.add(new DocumentRequest(id, purpose, status, documentName, dateRequest, remarks, dateProcessed));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get DocType By Id: " + ex.getMessage());
        }

        return documentType;
    }
    
    @Override
    public boolean getCheckDocumentRequestById(int id) {
        String query = "SELECT * FROM tbl_document_request WHERE id = ?";
        boolean is_check = false;
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_check = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Id: " + ex.getMessage());
        }
        return is_check;
    }
    
    @Override
    public boolean getCheckDocumentForDeleteRequestById(int id, int userId) {
        String query = "SELECT * FROM tbl_document_request WHERE id = ? and user_id = ? ";
        boolean is_check = false;
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            psmt.setInt(2, userId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_check = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Id: " + ex.getMessage());
        }
        return is_check;
    }
    
    @Override
    public boolean getCheckDocumentRequestPendingForById(int id) {
        String query = "SELECT * FROM tbl_document_request WHERE id = ? and status = 'PENDING'";
        boolean is_check = false;
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_check = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Id: " + ex.getMessage());
        }
        return is_check;
    }
    
    @Override
    public boolean getCheckDocumentRequestNotApprovedForById(int id) {
        String query = "SELECT * FROM tbl_document_request WHERE id = ? and status != 'APPROVED'";
        boolean is_check = false;
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_check = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Id: " + ex.getMessage());
        }
        return is_check;
    }
    
            
    @Override
    public boolean getCheckDocumentRequestPendingForCancelById(int id,int userId) {
        String query = "SELECT * FROM tbl_document_request WHERE id = ? and user_id = ? and status = 'PENDING'";
        boolean is_check = false;
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            psmt.setInt(2, userId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_check = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Get Document Request By Id: " + ex.getMessage());
        }
        return is_check;
    }

    @Override
    public boolean getCheckDocTypeById(int id) {
        String query = "SELECT * FROM tbl_document_type WHERE id = ?";
        boolean is_check = false;
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    is_check = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Get DocType By Id: " + ex.getMessage());
        }
        return is_check;
    }
    
    @Override
    public boolean createDocumentRequest(DocumentRequest documentRequest) {
        String query = "INSERT INTO tbl_document_request(user_id, document_id, purpose, status)"
                + "VALUES(?,?,?,?)";
        
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, documentRequest.getUser_id());
            psmt.setInt(2, documentRequest.getDocument_id());
            psmt.setString(3, documentRequest.getPurpose());
            psmt.setString(4, "PENDING");

            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Create User: " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean cancelDocumentRequest(int id) {
        String query = "UPDATE tbl_document_request SET status = 'CANCELLED' WHERE id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Update Document Request: " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public void approveDocumentRequestInsertStatus(int documentRequestId, int adminId, String remarks) {
        String query = "INSERT INTO tbl_document_request_status(document_request_id, admin_id, remarks)"
                + "VALUES(?,?,?)";
        
        try (Connection connect = dbConnection.connect();
            PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, documentRequestId);
            psmt.setInt(2, adminId);
            psmt.setString(3, remarks);
            psmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Create User: " + ex.getMessage());
           
        }
    }
    
    @Override
    public void approveDocumentRequestInsertPayment(int documentRequestId, int adminId, double amount, String receiptNo) {
        String query = "INSERT INTO tbl_document_request_payment(document_request_id, admin_id, amount, receipt_number)"
                + "VALUES(?,?,?,?)";
        
        try (Connection connect = dbConnection.connect();
            PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, documentRequestId);
            psmt.setInt(2, adminId);
            psmt.setDouble(3, amount);
            psmt.setString(4, receiptNo);
            psmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Create User: " + ex.getMessage());
           
        }
    }
    
    @Override
    public void rejectedDocumentRequestInsertStatus(int documentRequestId, int adminId, String remarks) {
        String query = "INSERT INTO tbl_document_request_status(document_request_id, admin_id, remarks)"
                + "VALUES(?,?,?)";
        
        try (Connection connect = dbConnection.connect();
            PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, documentRequestId);
            psmt.setInt(2, adminId);
            psmt.setString(3, remarks);
            psmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Create User: " + ex.getMessage());
           
        }
    }
    
    
    @Override
    public boolean rejectedDocumentRequest(int documentRequestId, int adminId) {
        
        String query = "UPDATE tbl_document_request SET status = 'REJECTED' WHERE id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, documentRequestId);
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Update Document Request: " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean approveDocumentRequest(int documentRequestId, int adminId) {
        
        String query = "UPDATE tbl_document_request SET status = 'APPROVED' WHERE id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, documentRequestId);
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Update Document Request: " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    
    public boolean deleteDocumentRequest(int id) {
        String query = "DELETE from tbl_document_request WHERE id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Delete Document Request: " + ex.getMessage());
            return false;
        }
    }
    @Override
    public void deleteDocumentRequestStatus(int id) {
        String query = "DELETE from tbl_document_request_status WHERE document_request_id=? ";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setInt(1, id);
            psmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Delete Document Request: " + ex.getMessage());
        
        }
    }
    
}
