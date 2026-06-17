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
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Board Operations")
@Story("Create Board")
public class TC_004_CreateBoardValidNameTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_004 - Verify board creation with a valid name")
    @Description("Verify board creation process when entering a valid name in 'name' field and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateBoardWithValidName(ITestContext context) {
        BoardPayload payload = BoardPayload.builder()
                .name("Test")
                .desc("Automated Test Board")
                .build();

        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "name", "Test");
        String boardId = response.jsonPath().getString("id");
        org.testng.Assert.assertNotNull(boardId, "Board ID was not generated in the response!");
        context.setAttribute("boardId", boardId);
        System.setProperty("boardId", boardId);

        logger.info("Successfully created Board with ID: {}", boardId);
    }
}