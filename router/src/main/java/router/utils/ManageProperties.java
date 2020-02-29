package utils;

import java.util.Properties;
import java.io.FileInputStream;
import java.lang.Thread;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ManageProperties {

    private String propertyFileName = "application.properties";
    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private String appConfigPath = rootPath + propertyFileName;
    private FileInputStream fileStream = null;

    private static ManageProperties instance = null;

    public static ManageProperties getInstance() {
        if (instance == null)
            instance = new ManageProperties();

        return instance;
    }

    public String getProp(String propertyName) {
        String returnProperty = null;

        try {
            this.fileStream = new FileInputStream(this.appConfigPath);

            Properties appProps = new Properties();
            appProps.load(fileStream);
            returnProperty = appProps.getProperty(propertyName);

        } catch (IOException ex1) {
            System.out.println("IOException: " + ex1.getMessage());
        }
        return returnProperty;
    }
}

