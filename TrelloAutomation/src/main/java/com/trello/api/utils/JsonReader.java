package com.trello.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonReader {
    private static final Logger logger = LogManager.getLogger(JsonReader.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readTestData(String filePath, Class<T> clazz) {
        try {
            File file = new File("src/test/resources/test-data/" + filePath);
            logger.info("Reading test data from file: {}", file.getAbsolutePath());
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            logger.error("Failed to read test data from: " + filePath, e);
            throw new RuntimeException("Error processing JSON test data file", e);
        }
    }
}