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
@Story("Update Board - Negative Path")
public class TC_010_UpdateBoardExceedingCharLimitTest extends BaseTest {

    private BoardClient boardClient;
    private String tempBoardId;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();

        BoardPayload payload = BoardPayload.builder()
                .name("Temp_Limit_Board")
                .desc("Temporary board for TC_010 update boundary verification")
                .build();

        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);

        tempBoardId = response.jsonPath().getString("id");
        logger.info("PRECONDITION: Created temporary Board with ID: {}", tempBoardId);
    }

    @Test(description = "TC_010 - Verify board update fails when name exceeds 17,000 characters")
    @Description("Verify board update process when entering a board name that exceeds the maximum character limit (17,000 characters) and entering valid credentials.")
    @Severity(SeverityLevel.MINOR) // Minor is used to match "Low" priority inside Allure's standard levels
    public void testUpdateBoardNameExceedingLimit() {
        String excessiveName = "a".repeat(17001);

        BoardPayload updatePayload = BoardPayload.builder()
                .name(excessiveName)
                .desc("Attempting to update board with an excessive name")
                .build();

        Response response = boardClient.updateBoard(tempBoardId, updatePayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for name");
        logger.info("Successfully verified that Trello rejects board updates when the name exceeds the maximum length limit.");
    }

    @AfterClass
    public void teardown() {
        if (tempBoardId != null) {
            logger.info("Teardown executing. Cleaning up temporary Board with ID: {}", tempBoardId);
            Response response = boardClient.deleteBoard(tempBoardId, validRequestSpec);
            ApiAssertions.assertStatusCode(response, 200);
        }
    }
}