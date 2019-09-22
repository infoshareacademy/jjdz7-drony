package com.korpodrony.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesService {
    public static final String APP_PATH = "app.path";

    private Properties prop = null;

    public String getProperty(String key) {
        if (prop == null) {
            loadProperties();
            if (prop == null){
                return null;
            }
        }
        return prop.getProperty(key);
    }

    private void loadProperties() {
        String path = "/home/patryk/Pulpit/Drony/jjdz7-drony/src/main/resources/config.properties";
        if (!Files.exists(Paths.get(path))){
            return;
        }
        try (FileInputStream input = new FileInputStream(path)) {
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
