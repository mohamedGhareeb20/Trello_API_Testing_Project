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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Board Operations")
@Story("Delete Board")
public class TC_007_DeleteBoardTest extends BaseTest {

    private BoardClient boardClient;
    private String tempBoardId;
    private boolean isDeletedSuccessfully = false;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
    }

    @Test(description = "TC_007 - Verify board deletion process")
    @Description("Verify board deletion process when selecting a valid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL) // Critical maps to "High" priority inside Allure's standard levels
    public void testDeleteBoard() {
        BoardPayload payload = BoardPayload.builder()
                .name("Temp_Delete_Board")
                .desc("Temporary board for TC_007 deletion verification")
                .build();

        Response createResponse = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(createResponse, 200);

        tempBoardId = createResponse.jsonPath().getString("id");
        Assert.assertNotNull(tempBoardId, "Precondition failed: Board ID was not generated!");
        logger.info("Precondition met: Created temporary Board with ID: {}", tempBoardId);
        Response deleteResponse = boardClient.deleteBoard(tempBoardId, validRequestSpec);
        ApiAssertions.assertStatusCode(deleteResponse, 200);
        ApiAssertions.assertBodyContainsText(deleteResponse, "_value");
        isDeletedSuccessfully = true;
        logger.info("Successfully deleted Board with ID: {}", tempBoardId);
    }

    @AfterClass
    public void teardown() {
        if (!isDeletedSuccessfully && tempBoardId != null) {
            logger.warn("Safety teardown executing. Cleaning up un-deleted Board with ID: {}", tempBoardId);
            try {
                boardClient.deleteBoard(tempBoardId, validRequestSpec);
            } catch (Exception e) {
                logger.error("Failed to execute safety board cleanup for ID: " + tempBoardId, e);
            }
        }
    }
}