package com.korpodrony.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//TODO change messages to polish langugage
public class PropertiesService {
    public static final String APP_PATH = "app.path";

    private Properties prop = null;

    public String getProperty(String key) {
        if (prop == null) {
            loadProperties();
            if (prop == null) {
                return null;
            }
        }
        return prop.getProperty(key);
    }

    private void loadProperties() {
        try (InputStream input
                     = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop = new Properties();
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
