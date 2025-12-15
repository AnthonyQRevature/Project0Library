package org.anthony.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_test {    // JDBC URL Format: jdbc:postgresql://host:port/database

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "secret";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try(Connection conn = getConnection()){

            System.out.println("Connected to Postgresql!");
            System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Version: " + conn.getMetaData().getDatabaseProductVersion());

        }catch(SQLException e){
            System.err.println("Connection Failed!");
            e.printStackTrace();
        }    
    }
}
