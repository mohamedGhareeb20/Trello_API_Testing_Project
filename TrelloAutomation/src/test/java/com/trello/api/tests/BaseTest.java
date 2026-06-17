package com.trello.api.tests;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import com.trello.api.specs.TrelloSpecBuilder;
import com.trello.api.utils.ConfigManager;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected RequestSpecification validRequestSpec;

    // Changed from @BeforeSuite to @BeforeClass to guarantee initialization per test class instance
    @BeforeClass
    public void setUpClass() {
        logger.info("Initializing standard request specification for class: {}", this.getClass().getSimpleName());
        validRequestSpec = TrelloSpecBuilder.getValidRequestSpec();
    }

    @AfterSuite
    public void tearDownSuite() {
        logger.info("Suite teardown sequence executing. Writing environment properties to Allure.");

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