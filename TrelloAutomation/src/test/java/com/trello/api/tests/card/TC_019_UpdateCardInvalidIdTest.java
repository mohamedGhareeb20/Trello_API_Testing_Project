package com.trello.api.tests.card;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.CardClient;
import com.trello.api.models.request.CardPayload;
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
@Story("Update Card - Negative Path")
public class TC_019_UpdateCardInvalidIdTest extends BaseTest {

    private CardClient cardClient;

    @BeforeClass
    public void setup() {
        cardClient = new CardClient();
    }

    @Test(description = "TC_019 - Verify card update fails with a non-existent Card ID")
    @Description("Verify card update process when entering a non-existent but structurally valid Card ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateCardWithNonExistentId(ITestContext context) {
        // Step 1: Use a structurally valid (24-character hex) but non-existent Card ID to trigger 404
        String nonExistentCardId = "610000000000000000000000";

        CardPayload cardPayload = CardPayload.builder()
                .name("Updated Card Name")
                .desc("This card was updated via API")
                .build();

        Response response = cardClient.updateCard(nonExistentCardId, cardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 404);
        ApiAssertions.assertBodyContainsText(response, "The requested resource was not found");
        logger.info("Successfully verified that Trello returns 404 when updating a non-existent Card ID.");
    }
}