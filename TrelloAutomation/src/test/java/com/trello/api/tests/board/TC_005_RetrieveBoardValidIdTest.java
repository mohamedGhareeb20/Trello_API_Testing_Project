package com.trello.api.tests.board;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.models.response.BoardResponse;
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
@Story("Retrieve Board")
public class TC_005_RetrieveBoardValidIdTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_005 - Verify board retrieval with a valid Board ID")
    @Description("Verify board retrieval process when entering a valid Board ID in the URL path and entering valid credentials in the query parameters.")
    @Severity(SeverityLevel.CRITICAL)
    public void testRetrieveBoardWithValidId(ITestContext context) {
        String boardId = (String) context.getAttribute("boardId");
        if (boardId == null) {
            boardId = System.getProperty("boardId");
        }

        Assert.assertNotNull(boardId, "Board ID not found in TestNG context or System properties. Please run TC_004 first.");
        Response response = boardClient.getBoard(boardId, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        BoardResponse boardResponse = response.as(BoardResponse.class);
        Assert.assertEquals(boardResponse.getId(), boardId, "Retrieved Board ID does not match expected ID!");
        Assert.assertEquals(boardResponse.getName(), "Test", "Retrieved Board Name does not match expected Name!");
        Assert.assertEquals(boardResponse.getClosed(), Boolean.FALSE, "Retrieved Board should be active (closed: false)!");

        logger.info("Successfully retrieved details for Board ID: {} (Name: '{}')", boardId, boardResponse.getName());
    }
}