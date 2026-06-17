package com.trello.api.tests;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import com.trello.api.specs.TrelloSpecBuilder;
import com.trello.api.utils.ConfigManager;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected RequestSpecification validRequestSpec;

    @BeforeSuite
    public void setUpSuite() {
        logger.info("Suite setup sequence executing. Initializing standard request specifications.");
        validRequestSpec = TrelloSpecBuilder.getValidRequestSpec();
    }

    @AfterSuite
    public void tearDownSuite() {
        logger.info("Suite teardown sequence executing. Writing environment properties to Allure.");

        // Writes target environment metadata dynamically into the allure-results folder
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Environment", "Trello Sandbox")
                        .put("Base URL", ConfigManager.get("baseUrl"))
                        .put("Java Version", System.getProperty("java.version"))
                        .put("Operating System", System.getProperty("os.name"))
                        .build(),
                System.getProperty("user.dir") + "/test-output/target/allure-results/"
        );
    }
}