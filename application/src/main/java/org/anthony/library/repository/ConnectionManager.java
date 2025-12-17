package org.anthony.library.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection _connection;

    static {
        if (_connection == null) {
            Properties properties = new Properties();

            try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("database.properties")) {

                if(input == null){
                    throw new Exception("Unable to find database.properties");
                }else{
                    properties.load(input);
                }

                // Load JDBC Driver
                Class.forName(properties.getProperty("db.driver"));

                //Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
                _connection = DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password")
                );

            }catch(IOException | ClassNotFoundException e){
                throw new RuntimeException("Failed to load database configuration");
            }
            catch(SQLException e)
            {
                throw new RuntimeException("Failed to connect to database");
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection()
    {
        return _connection;
    }
}
