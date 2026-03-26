/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * 
 */
public class DbConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/db_brdrms";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";
    
    public Connection connect() throws SQLException{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    
}


}
