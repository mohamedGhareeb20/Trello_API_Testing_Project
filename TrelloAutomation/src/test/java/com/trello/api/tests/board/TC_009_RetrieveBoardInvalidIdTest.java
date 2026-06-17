package com.trello.api.tests.board;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
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
@Story("Retrieve Board - Negative Path")
public class TC_009_RetrieveBoardInvalidIdTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_009 - Verify board retrieval fails with a non-existent Board ID")
    @Description("Verify board retrieval process when entering a non-existent but structurally valid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.NORMAL)
    public void testRetrieveBoardWithInvalidId() {

        String nonExistentBoardId = "610000000000000000000000";
        Response response = boardClient.getBoard(nonExistentBoardId, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 404);
        ApiAssertions.assertBodyContainsText(response, "The requested resource was not found");
        logger.info("Successfully verified that Trello returns 404 for a non-existent Board ID.");
    }
}