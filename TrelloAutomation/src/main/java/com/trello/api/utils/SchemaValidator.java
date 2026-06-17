package com.trello.api.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SchemaValidator {
    private static final Logger logger = LogManager.getLogger(SchemaValidator.class);

    public static void validateSchema(ValidatableResponse response, String schemaFileName) {
        logger.info("Validating response schema against contract file: schemas/{}", schemaFileName);
        response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }
}