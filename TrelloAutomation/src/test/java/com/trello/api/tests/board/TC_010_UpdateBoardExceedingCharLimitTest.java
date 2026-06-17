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
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Board Operations")
@Story("Update Board - Negative Path")
public class TC_010_UpdateBoardExceedingCharLimitTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_010 - Verify board update fails when name exceeds 17,000 characters")
    @Description("Verify board update process when entering a board name that exceeds the maximum character limit (17,000 characters) and entering valid credentials.")
    @Severity(SeverityLevel.MINOR)
    public void testUpdateBoardNameExceedingLimit(ITestContext context) {
        String boardId = (String) context.getAttribute("boardId");
        if (boardId == null) {
            boardId = System.getProperty("boardId");
        }

        Assert.assertNotNull(boardId, "Board ID not found in TestNG context or System properties. Please run TC_004 first.");
        String excessiveName = "a".repeat(17001);
        BoardPayload payload = BoardPayload.builder()
                .name(excessiveName)
                .desc("Attempting to update board with an excessive name")
                .build();

        Response response = boardClient.updateBoard(boardId, payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for name");
        logger.info("Successfully verified that Trello rejects board updates when the name exceeds the maximum length limit.");
    }
}