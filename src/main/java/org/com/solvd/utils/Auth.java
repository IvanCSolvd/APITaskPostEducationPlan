package org.com.solvd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Auth {
    private static String bearerToken = null;

    public static String getTokenFromPropertiesFile() {
        if (bearerToken == null) {
            Properties properties = new Properties();
            InputStream inputStream = Auth.class.getClassLoader().getResourceAsStream("api.properties");
            try {
                properties.load(inputStream);
                bearerToken = properties.getProperty("primary_token");
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return bearerToken;
    }
}
