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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Card Operations")
@Story("Create Card - Negative Path")
public class TC_018_CreateCardEmptyListIdTest extends BaseTest {

    private CardClient cardClient;

    @BeforeClass
    public void setup() {
        cardClient = new CardClient();
    }

    @Test(description = "TC_018 - Verify card creation fails when idList is empty")
    @Description("Verify card creation process when leaving the mandatory 'idList' field empty in the parameters and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL) // Critical maps to "High" priority inside Allure's standard levels
    public void testCreateCardWithEmptyListId() {
        CardPayload cardPayload = CardPayload.builder()
                .name("test")
                .idList("") // Left empty per the manual test case specification
                .build();

        Response response = cardClient.createCard(cardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for idList");
        logger.info("Successfully verified that Trello rejects card creation when idList is empty.");
    }
}