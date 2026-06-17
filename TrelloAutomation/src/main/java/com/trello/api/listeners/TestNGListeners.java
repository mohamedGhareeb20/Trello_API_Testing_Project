package com.trello.api.listeners;

import com.trello.api.utils.report.AllureAttachmentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListeners implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestNGListeners.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Initializing Test Suite Execution Sequence: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Execution Sequence Completed: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("-------------------- Starting Test Execution: " + result.getMethod().getMethodName() + " --------------------");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Result: PASS - Executed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Result: FAIL - Executed: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            logger.error("Exception message context: ", result.getThrowable());
            AllureAttachmentManager.attachTextLog("Failure Exception Details: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Result: SKIP - Executed: " + result.getMethod().getMethodName());
    }
}