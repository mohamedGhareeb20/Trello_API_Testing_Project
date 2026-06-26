package com.trello.api.tests.list;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.BoardPayload;
import com.trello.api.models.request.ListPayload;
import com.trello.api.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("List Operations")
@Story("Create List - Negative Path")
public class TC_014_CreateListEmptyNameTest extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private String tempBoardId;
    private boolean isBoardDeleted = false;

    @BeforeClass
    public void setup() {
        listClient = new ListClient();
        boardClient = new BoardClient();

        BoardPayload payload = BoardPayload.builder()
                .name("Temp_Board_For_List_Validation")
                .desc("Temporary board for TC_014 list creation verification")
                .build();

        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        tempBoardId = response.jsonPath().getString("id");
        logger.info("PRECONDITION: Created temporary Board with ID: {}", tempBoardId);
    }

    @Test(description = "TC_014 - Verify list creation fails when name is empty")
    @Description("Verify list creation process when leaving the mandatory 'name' field empty and selecting a valid Board ID with valid credentials.")
    @Severity(SeverityLevel.NORMAL) // Normal maps to "Medium" priority inside Allure's standard levels
    public void testCreateListWithEmptyName(ITestContext context) {
        ListPayload listPayload = ListPayload.builder()
                .name("") // Un-commented and set explicitly to match manual test data "name: """
                .idBoard(tempBoardId)
                .build();

        Response response = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for name");
    }

    @AfterClass
    public void teardown() {
        if (!isBoardDeleted && tempBoardId != null) {
            logger.info("Teardown executing. Cleaning up temporary Board with ID: {}", tempBoardId);
            try {
                boardClient.deleteBoard(tempBoardId, validRequestSpec);
                isBoardDeleted = true;
            } catch (Exception e) {
                logger.error("Failed to execute board cleanup for ID: " + tempBoardId, e);
            }
        }
    }
}