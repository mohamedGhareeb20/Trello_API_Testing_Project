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
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Board Operations")
@Story("Delete Board")
public class TC_007_DeleteBoardTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_007 - Verify board deletion process")
    @Description("Verify board deletion process when selecting a valid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteBoard(ITestContext context) {
        // Step 1: Retrieve the boardId from the test context or system properties
        String boardId = (String) context.getAttribute("boardId");
        if (boardId == null) {
            boardId = System.getProperty("boardId");
        }

        Assert.assertNotNull(boardId, "Board ID not found in TestNG context or System properties. Please run TC_004 first.");
        Response response = boardClient.deleteBoard(boardId, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertBodyContainsText(response, "_value");
        logger.info("Successfully deleted Board with ID: {}", boardId);
        context.removeAttribute("boardId");
        System.clearProperty("boardId");
    }
}