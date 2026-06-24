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
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("List Operations")
@Story("Create List")
public class TC_011_CreateListTest extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private String tempBoardId;
    private boolean isBoardDeleted = false;

    @BeforeClass
    public void setup() {
        listClient = new ListClient();
        boardClient = new BoardClient();

        // 1. PRECONDITION: Create temporary board to hold the list
        BoardPayload payload = BoardPayload.builder()
                .name("Temp_Board_For_List_Creation")
                .desc("Temporary board for TC_011 list creation verification")
                .build();

        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        tempBoardId = response.jsonPath().getString("id");
        logger.info("PRECONDITION: Created temporary Board with ID: {}", tempBoardId);
    }

    @Test(description = "TC_011 - Verify list creation process")
    @Description("Verify create list process when selecting a valid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateList(ITestContext context) {
        // 2. TEST: Build payload and execute POST request to Trello
        ListPayload listPayload = ListPayload.builder()
                .name("Test_List")
                .idBoard(tempBoardId)
                .build();

        Response response = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "name", "Test_List"); // Fixed assertion value to match payload
        ApiAssertions.assertFieldEquals(response, "idBoard", tempBoardId);
        String listId = response.jsonPath().getString("id");
        Assert.assertNotNull(listId, "List ID was not generated in the response!");
        context.setAttribute("listId", listId);
        System.setProperty("listId", listId);

        logger.info("Successfully created List with ID: {}", listId);
    }

    @AfterClass
    public void teardown() {
        // 3. POST-CONDITION/CLEANUP: Clean up the temporary board from the server
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
