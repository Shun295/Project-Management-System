package com.projectmanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load properties file (not auto-closed)
            InputStream input = DBConnUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("⚠️ db.properties not found in classpath!");
            }

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // Create a NEW connection every time safely
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error loading DB properties: " + e.getMessage());
        }

        return connection;
    }
}
