
package com.joysistvi.brdrms.impl;

import com.joysistvi.brdrms.repo.DocTypeRepo;
import com.joysistvi.brdrms.model.DocumentType;
import com.joysistvi.brdrms.util.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class DocTypeRepoImpl implements DocTypeRepo {

    private final DbConnection dbConnection;

    public DocTypeRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean createDocType(DocumentType dt) {
        String query = "INSERT INTO tbl_document_type (document_name,document_fee) VALUES (?,?)";
        
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            psmt.setString(1, dt.getDocumentName());
           
            psmt.setDouble(2, dt.getFee());
            
            if (psmt.executeUpdate() > 0) {
                try (ResultSet k = psmt.getGeneratedKeys()) {
                    if (k.next()) {
                        dt.setId(k.getInt(1));
                    }
                }
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Create DocType: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public List<DocumentType> getDocTypes() {
        String query = "SELECT id, document_name, document_fee, is_archived, updated_at FROM tbl_document_type ORDER BY id ASC";
        List<DocumentType> docTypes = new ArrayList<>();
        
        try (Connection connect = dbConnection.connect();
             Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(query)) {
             
            while (result.next()) {
                docTypes.add(mapRow(result));
            }
        } catch (SQLException ex) {
            System.out.println("Get DocTypes: " + ex.getMessage());
        }
        return docTypes;
    }

    @Override
    public List<DocumentType> getActiveDocTypes() {
        String query = "SELECT id, document_name, document_fee, updated_at, is_archived FROM tbl_document_type WHERE is_archived = 0 ORDER BY id ASC";
        List<DocumentType> docTypes = new ArrayList<>();
        
        try (Connection connect = dbConnection.connect();
             Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(query)) {
             
            while (result.next()) {
                docTypes.add(mapRow(result));
            }
        } catch (SQLException ex) {
            System.out.println("Get Active DocTypes: " + ex.getMessage());
        }
        return docTypes;
    }

    @Override
    public DocumentType getDocTypeById(int id) {
        String query = "SELECT id, document_name, document_fee, is_archived, updated_at FROM tbl_document_type WHERE id = ?";
        
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception ex) {
            System.out.println("Get DocType By Id: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateDocType(DocumentType dt) {
        String query = "UPDATE tbl_document_type SET document_name=?, document_fee=?, updated_at=? WHERE id=?";
        
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setString(1, dt.getDocumentName());
            psmt.setDouble(2, dt.getFee());
            psmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            psmt.setInt(4, dt.getId());
            
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Update DocType: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean archiveDocType(int id) {
        String query = "UPDATE tbl_document_type SET is_archived=1, updated_at=? WHERE id=?";
        
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psmt.setInt(2, id);
            
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Archive DocType: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean restoreDocType(int id) {
        String query = "UPDATE tbl_document_type SET is_archived=0, updated_at=? WHERE id=?";
        
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psmt.setInt(2, id);
            
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Restore DocType: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteDocType(int id) {
        String query = "DELETE FROM tbl_document_type WHERE id=?";
        
        try (Connection connect = dbConnection.connect();
             PreparedStatement psmt = connect.prepareStatement(query)) {
             
            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Delete DocType: " + ex.getMessage());
        }
        return false;
    }

    private DocumentType mapRow(ResultSet rs) throws SQLException {
        return new DocumentType(
            rs.getInt("id"),
            rs.getString("document_name"),
            rs.getDouble("document_fee"),
            rs.getInt("is_archived") == 1,
            rs.getTimestamp("updated_at")
        );
    }
}