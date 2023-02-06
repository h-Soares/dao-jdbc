package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {
    
    public static Connection getConnection() {
        Connection conn = null;
            try {
                Properties prop = loadProperties();
                String dburl = prop.getProperty("dburl");
                conn = DriverManager.getConnection(dburl, prop);
            }
            catch(SQLException e) {
                throw new DBException(e.getMessage());
            }
        return conn;
    }
/* 
    public static void closeConnection(Connection conn) {
        try {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    } */
    
    private static Properties loadProperties() {
        try (FileInputStream fileIn = new FileInputStream("./properties/database.properties")) {
            Properties prop = new Properties();
            prop.load(fileIn);
            return prop;
        }
        catch(IOException e) {
            throw new DBException("ERROR: " + e.getMessage()); 
        } 
    }
}