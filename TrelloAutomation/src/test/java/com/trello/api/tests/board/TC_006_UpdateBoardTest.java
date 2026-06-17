package com.trello.api.tests.board;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.models.request.BoardPayload;
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
@Story("Update Board")
public class TC_006_UpdateBoardTest extends BaseTest {

    private BoardClient boardClient;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_006 - Verify board update process")
    @Description("Verify board update process when entering valid new name and description in the input fields and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateBoardDetails(ITestContext context) {
        String boardId = (String) context.getAttribute("boardId");
        if (boardId == null) {
            boardId = System.getProperty("boardId");
        }

        Assert.assertNotNull(boardId, "Board ID not found in TestNG context or System properties. Please run TC_004 first.");

        BoardPayload updatePayload = BoardPayload.builder()
                .name("Updated_Test")
                .desc("This board has been updated")
                .build();

        Response response = boardClient.updateBoard(boardId, updatePayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        BoardResponse boardResponse = response.as(BoardResponse.class);
        Assert.assertEquals(boardResponse.getId(), boardId, "Board ID mismatch in update response!");
        Assert.assertEquals(boardResponse.getName(), "Updated_Test", "Board name was not updated correctly!");
        Assert.assertEquals(boardResponse.getDesc(), "This board has been updated", "Board description was not updated correctly!");

        logger.info("Successfully updated details for Board ID: {} (New Name: '{}')", boardId, boardResponse.getName());
    }
}