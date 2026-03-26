/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.impl;

import com.joysistvi.brdrms.repo.UserRepo;
import com.joysistvi.brdrms.model.User;
import com.joysistvi.brdrms.util.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Hacutina
 */
public class UserRepoImpl implements UserRepo{

    private final DbConnection dbConnection;

    public UserRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> getUser() { // get approved user
        String query = "SELECT * FROM tbl_users WHERE is_archived = 0 AND status = 'APPROVED'  ";

        List<User> Users = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            while (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fullName = result.getString("full_name");
                String contactNumber = result.getString("contact_number");
                String address = result.getString("address");
                String role = (result.getString("role"));
                Timestamp dateStatus = (result.getTimestamp("date_status"));

                Users.add(new User(id, username, password, fullName, contactNumber, address, role, dateStatus));
            }
        } catch (SQLException ex) {
            System.out.println("Get Users: " + ex.getMessage());
        }
        return Users;
    }
    
    @Override
    public List<User> getUserViewById(int userId) { // get approved user
        String query = "SELECT * FROM tbl_users WHERE id = ? AND status = 'APPROVED'  ";

        List<User> users = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, userId);
            try (ResultSet result = psmt.executeQuery()) {
                while (result.next()) {
                    
                    int id = result.getInt("id");
                    String fullname = result.getString("full_name");
                    String contactNumber = result.getString("contact_number");
                    String address = result.getString("address");
                    //double documentFee = result.getDouble("document_fee");

                    users.add(new User(id, fullname, contactNumber, address));
                }
            }
        } catch (Exception ex) {
            System.out.println("Get User By Id: " + ex.getMessage());
        }
        return users;
    }
    
    @Override
    public List<User> getAllUser() { // get approved, rejected user
        String query = "SELECT * FROM tbl_users WHERE is_archived = 0 AND status IN ('APPROVED','REJECTED')";

        List<User> Users = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            while (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fullName = result.getString("full_name");
                String contactNumber = result.getString("contact_number");
                String address = result.getString("address");
                String role = result.getString("role");
                String status = result.getString("status");
                Timestamp dateStatus = (result.getTimestamp("date_status"));

                Users.add(new User(id, username, password, fullName, contactNumber, address, role,status, dateStatus));
            }
        } catch (SQLException ex) {
            System.out.println("Get All Users: " + ex.getMessage());
        }
        return Users;
    }

    @Override
    public List<User> getPendingUser() { // get pendin user
        String query = "SELECT * FROM tbl_users WHERE is_archived = 0 AND status = 'PENDING'  ";

        List<User> Users = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            while (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fullName = result.getString("full_name");
                String contactNumber = result.getString("contact_number");
                String address = result.getString("address");
                String role = (result.getString("role"));

                Users.add(new User(id, username, password, fullName, contactNumber, address, role));
            }
        } catch (SQLException ex) {
            System.out.println("Get Pending Users: " + ex.getMessage());
        }
        return Users;
    }
    
    @Override
    public List<User> getRejectedUser() {
        String query = "SELECT * FROM tbl_users WHERE is_archived = 0 AND status = 'REJECTED'  ";

        List<User> Users = new ArrayList<>();
        try (Connection connect = dbConnection.connect();
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            while (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fullName = result.getString("full_name");
                String contactNumber = result.getString("contact_number");
                String address = result.getString("address");
                String role = (result.getString("role"));
                String status = (result.getString("status"));
                Timestamp dateStatus = (result.getTimestamp("date_status"));

                Users.add(new User(id, username, password, fullName, contactNumber, address, role,status, dateStatus));
            }
        } catch (SQLException ex) {
            System.out.println("Get Rejected Users: " + ex.getMessage());
        }
        return Users;
    }

    @Override
    public boolean approveUser(int id) {
        String query = "UPDATE tbl_users SET status = ?, date_status=? WHERE id=?";
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setString(1, "APPROVED");
            psmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            psmt.setInt(3, id);
     

            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Approve User: " + ex.getMessage());
        }
        return false;
    }
    
    
    @Override
    public boolean rejectUser(int id) {
        String query = "UPDATE tbl_users SET status = ?, date_status=? WHERE id=?";
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setString(1, "REJECTED");
            psmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            psmt.setInt(3, id);
     

            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Reject User: " + ex.getMessage());
        }
        return false;
    }
    @Override
    public String[] verifyLogin(String username, String password) {
        String[] userDetails = null;
        String query = "SELECT * FROM tbl_users WHERE username = ? AND is_archived = 0 and status = 'APPROVED'";

        try (Connection connect = dbConnection.connect();
             PreparedStatement prep = connect.prepareStatement(query)) {

            prep.setString(1, username);
            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    // Compare raw password with stored bcrypt hash
                    if (BCrypt.checkpw(password, storedHash)) {
                        userDetails = new String[] {
                            rs.getString("id"),
                            rs.getString("full_name"),
                            rs.getString("role")
                        };
                    }
                }
            }
            return userDetails;

        } catch (Exception e) {
            System.out.println("Login : " + e.getMessage());
            return null;
        }
    }


    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO tbl_users(username, password, full_name, contact_number, address, role, status)"
                + "VALUES(?,?,?,?,?,?,?)";

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, hashedPassword);
            psmt.setString(3, user.getFullName());
            psmt.setString(4, user.getContactNumber());
            psmt.setString(5, user.getAddress());
            psmt.setString(6, "USER");
            psmt.setString(7, "PENDING");

            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Create User: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean createUserAdmin(User user) {
        String query = "INSERT INTO tbl_users(username, password, full_name, contact_number, address, role, status)"
                + "VALUES(?,?,?,?,?,?,?)";

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, hashedPassword);
            psmt.setString(3, user.getFullName());
            psmt.setString(4, user.getContactNumber());
            psmt.setString(5, user.getAddress());
            psmt.setString(6, "ADMIN");
            psmt.setString(7, "APPROVED");

            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Create User Admin: " + ex.getMessage());
            return false;
        }
    }
    
    
    @Override
    public boolean updateUserInfo(User user) {
        String query = "UPDATE tbl_users SET username=?, password=?, full_name=?, contact_number=?, address=? WHERE id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, user.getPassword());
            psmt.setString(3, user.getFullName());
            psmt.setString(4, user.getContactNumber());
            psmt.setString(5, user.getAddress());
            psmt.setInt(6, user.getId());
           
            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Update User: " + ex.getMessage());
            return false;
        }
    }
   
    @Override
    public boolean deleteUser(int id) {
        String query = "DELETE FROM tbl_users where id = ?";
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Delete User: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public List<User> searchUser(String searchKeyword) {
        String query = "SELECT * FROM tbl_users WHERE is_archived = 0 AND ("
                + "username LIKE ? OR full_name LIKE ? OR contact_number LIKE ? OR address LIKE ? OR role LIKE ? OR status LIKE ?"
                + ") AND status IN ('APPROVED','PENDING','REJECTED')";

        List<User> Users = new ArrayList<>();

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setString(1, "%" + searchKeyword + "%");
            psmt.setString(2, "%" + searchKeyword + "%");
            psmt.setString(3, "%" + searchKeyword + "%");
            psmt.setString(4, "%" + searchKeyword + "%");
            psmt.setString(5, "%" + searchKeyword + "%");
            psmt.setString(6, "%" + searchKeyword + "%");
         

            try (ResultSet result = psmt.executeQuery()) {
            while (result.next()) {
                Users.add(new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("full_name"),
                        result.getString("contact_number"),
                        result.getString("address"),
                        result.getString("role"),
                        result.getString("status"),
                        result.getTimestamp("date_status")));

            }
            } // end try-with-resources ResultSet

        } catch (SQLException ex) {
            System.out.println("Search User: " + ex.getMessage());
        }

        return Users;
    }
  
    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM tbl_users WHERE id = ? AND is_archived = 0";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setUserName(result.getString("username"));
                    user.setPassword(result.getString("password"));
                    user.setFullName(result.getString("full_name"));
                    user.setContactNumber(result.getString("contact_number"));
                    user.setAddress(result.getString("address"));
                    user.setRole(result.getString("role"));
                    user.setStatus(result.getString("status"));

                    return user;
                }
            }

        } catch (Exception ex) {
            System.out.println("Fetch error: " + ex.getMessage());
        }
        return null;
    }
    
    @Override
     public User getPendingUserById(int id) {
        String query = "SELECT * FROM tbl_users WHERE id = ? AND status = ?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {
            
            psmt.setInt(1, id);
            psmt.setString(2, "PENDING");
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setUserName(result.getString("username"));
                    user.setPassword(result.getString("password"));
                    user.setFullName(result.getString("full_name"));
                    user.setContactNumber(result.getString("contact_number"));
                    user.setAddress(result.getString("address"));
                    user.setRole(result.getString("role"));
                    user.setStatus(result.getString("status"));
                    user.setDateStatus(result.getTimestamp("date_status"));

                    return user;
                }
            }

        } catch (Exception ex) {
            System.out.println(" getPendingUserById error: " + ex.getMessage());
        }
        return null;
    }
   
    @Override
    public boolean isUsernameExist(String username) {
        String query = "SELECT COUNT(*) FROM tbl_users WHERE username = ?";
        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setString(1, username);
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1) > 0;
                }
            }
        } catch (Exception ex) {
            System.out.println("Is Username Exist: " + ex.getMessage());
        }
        return false; // default if error
    }

}

/*
 //repo
    public boolean archiveUser(int id) {
        String query = "UPDATE tblusers SET is_archived=1 WHERE id=?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement psmt = connect.prepareStatement(query)) {

            psmt.setInt(1, id);

            return psmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Archive User: " + ex.getMessage());
        }
        return false;
    }

    //view 
    public void archiveUser() {
        Scanner scanner = new Scanner(System.in);
        UserRepoImpl ur = new UserRepoImpl(dbConnection);
        System.out.print("Enter Usert ID to archive: ");
        int archivedID = Integer.parseInt(scanner.nextLine());
        User existingUser = ur.getUserById(archivedID);;
        if (existingUser != null) {
            if (ur.archiveUser(archivedID)) {
                System.out.println("Archived Succefully");
            } else {
                System.out.println("Archived Failed");
            }
        } else {
            System.out.println("Resident not found!");
        }
    }

//    public String[] verifyLogin(String username, String password) {
//        String[] userDetails = new String[3];
//        String query = "SELECT * FROM tblusers where username = ? and password = ? and  is_archived = 0";
//
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//        
//        
//        try (Connection connect = dbConnection.connect();
//                PreparedStatement prep = connect.prepareStatement(query)) {
//
//            prep.setString(1, username);
//            prep.setString(2, hashedPassword);
//            ResultSet rs = prep.executeQuery();
//            while (rs.next()) {
//                userDetails[0] = rs.getString("id");
//                userDetails[1] = rs.getString("full_name");
//                userDetails[2] = rs.getString("role");
//
//            }
//            return userDetails;
//
//        } catch (Exception e) {
//            System.out.println("Login : " + e.getMessage());
//            return userDetails;
//        }
//
//    }











*/