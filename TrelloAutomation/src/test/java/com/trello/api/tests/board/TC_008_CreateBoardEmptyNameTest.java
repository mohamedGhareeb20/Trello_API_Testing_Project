package com.trello.api.tests.board;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.models.request.BoardPayload;
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
@Feature("Board Operations")
@Story("Create Board - Negative Path")
public class TC_008_CreateBoardEmptyNameTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_008 - Verify board creation fails when name is empty")
    @Description("Verify board creation process when leaving the mandatory 'name' field empty in the parameters and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL) // Critical used to match "High" priority within Allure's standard levels
    public void testCreateBoardWithEmptyName() {
        BoardPayload payload = BoardPayload.builder()
                .name("")
                .desc("Attempting to create board with empty name")
                .build();

        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for name");
        logger.info("Successfully verified that Trello rejects board creation when name is empty.");
    }
}