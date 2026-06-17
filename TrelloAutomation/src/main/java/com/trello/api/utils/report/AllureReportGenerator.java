package com.trello.api.utils.report;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllureReportGenerator {
    private static final Logger logger = LogManager.getLogger(AllureReportGenerator.class);

    public static void injectAllureMetadata() {
        logger.info("Injecting test environment context details into Allure report.");
        // Placeholders can be dynamically loaded if using environment-level configurations later
    }
}