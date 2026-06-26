package com.trello.api.tests.checklist;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.ChecklistClient;
import com.trello.api.models.request.ChecklistPayload;
import com.trello.api.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Checklist Operations")
@Story("Create Checklist - Negative Path")
public class TC_022_CreateChecklistInvalidCardIdTest extends BaseTest {

    private ChecklistClient checklistClient;

    @BeforeClass
    public void setup() {
        checklistClient = new ChecklistClient();
    }

    @Test(description = "TC_022 - Verify checklist creation fails with malformed Card ID")
    @Description("Verify checklist creation process when entering a malformed Card ID ('invalid') in the 'idCard' field and entering valid credentials.")
    @Severity(SeverityLevel.MINOR) // Minor maps to 'Low' priority inside Allure's standard levels
    public void testCreateChecklistWithMalformedCardId(ITestContext context) {
        String malformedCardId = "invalid";

        ChecklistPayload checklistPayload = ChecklistPayload.builder()
                .name("Test")
                .idCard(malformedCardId)
                .build();

        Response response = checklistClient.createChecklist(checklistPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for idCard");
        logger.info("Successfully verified Trello rejects checklist creation on a malformed Card ID with 400.");
    }
}