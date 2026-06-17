package com.trello.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("environment.properties")) {
            if (input == null) {
                logger.error("Unable to find environment.properties file.");
            } else {
                properties.load(input);
                logger.info("Successfully loaded environment.properties configuration.");
            }
        } catch (IOException ex) {
            logger.error("Exception encountered loading environment properties", ex);
        }
    }

    public static String get(String key) {
        String systemProp = System.getProperty(key);
        if (systemProp != null) {
            return systemProp;
        }
        return properties.getProperty(key);
    }
}