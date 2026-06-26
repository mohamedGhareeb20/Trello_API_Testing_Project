package com.trello.api.tests.card;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.CardClient;
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
@Feature("Card Operations")
@Story("Retrieve Card - Negative Path")
public class TC_020_RetrieveCardInvalidIdTest extends BaseTest {

    private CardClient cardClient;

    @BeforeClass
    public void setup() {
        cardClient = new CardClient();
    }

    @Test(description = "TC_020 - Verify card retrieval fails with a non-existent Card ID")
    @Description("Verify card retrieval process when entering a non-existent but structurally valid Card ID in the URL path and entering valid credentials in the query parameters.")
    @Severity(SeverityLevel.NORMAL) // Normal maps to "Medium" priority inside Allure's standard levels
    public void testRetrieveCardWithNonExistentId(ITestContext context) {
        String nonExistentCardId = "610000000000000000000000";
        Response response = cardClient.getCard(nonExistentCardId, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 404);
        ApiAssertions.assertBodyContainsText(response, "The requested resource was not found");
        logger.info("Successfully verified that Trello returns 404 when retrieving a non-existent Card ID.");
    }
}