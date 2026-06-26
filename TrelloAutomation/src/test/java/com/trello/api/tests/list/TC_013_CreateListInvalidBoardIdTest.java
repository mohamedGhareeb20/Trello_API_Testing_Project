package com.trello.api.tests.list;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.ListPayload;
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
@Feature("List Operations")
@Story("Create List - Negative Path")
public class TC_013_CreateListInvalidBoardIdTest extends BaseTest {

    private ListClient listClient;

    @BeforeClass
    public void setup() {
        listClient = new ListClient();
    }

    @Test(description = "TC_013 - Verify list creation fails with invalid Board ID")
    @Description("Verify list creation process when entering an invalid Board ID in the 'idBoard' field and entering valid credentials.")
    @Severity(SeverityLevel.NORMAL) // Normal maps to "Medium" priority inside Allure's standard levels
    public void testCreateListWithInvalidBoardId(ITestContext context) {
        ListPayload listPayload = ListPayload.builder()
                .name("Test_List")
                .idBoard("invalid_id")
                .build();

        Response response = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for idBoard");
        logger.info("Successfully verified that Trello rejects list creation when the board ID is malformed.");
    }
}