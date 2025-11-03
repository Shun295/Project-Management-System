package com.projectmanagement.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static final String PROPERTY_FILE = "db.properties";

    public static String getPropertyString(String key) {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTY_FILE)) {
            prop.load(fis);
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

