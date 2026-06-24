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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("Board Operations")
@Story("Retrieve Board")
public class TC_005_RetrieveBoardValidIdTest extends BaseTest {

    private BoardClient boardClient;
    private String tempBoardId;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
        BoardPayload payload = BoardPayload.builder()
                .name("TC_005_Temp_Board")
                .desc("Temporary board for TC_005 retrieval verification")
                .build();

        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        tempBoardId = response.jsonPath().getString("id");
        logger.info("PRECONDITION: Created temporary Board with ID: {}", tempBoardId);
    }

    @Test(description = "TC_005 - Verify board retrieval with a valid Board ID")
    @Description("Verify board retrieval process when entering a valid Board ID in the URL path and entering valid credentials in the query parameters.")
    @Severity(SeverityLevel.CRITICAL) // Critical used to match "High" priority within Allure's standard levels
    public void testRetrieveBoardWithValidId() {
        Response response = boardClient.getBoard(tempBoardId, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        BoardResponse boardResponse = response.as(BoardResponse.class);
        Assert.assertEquals(boardResponse.getId(), tempBoardId, "Retrieved Board ID does not match expected ID!");
        Assert.assertEquals(boardResponse.getName(), "TC_005_Temp_Board", "Retrieved Board Name does not match expected Name!");
        Assert.assertEquals(boardResponse.getClosed(), Boolean.FALSE, "Retrieved Board should be active (closed: false)!");
        logger.info("Successfully retrieved details for Board ID: {} (Name: '{}')", tempBoardId, boardResponse.getName());
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