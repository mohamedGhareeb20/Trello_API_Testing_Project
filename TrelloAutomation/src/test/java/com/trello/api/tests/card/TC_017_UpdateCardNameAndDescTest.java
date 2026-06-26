package com.trello.api.tests.card;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.clients.CardClient;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.BoardPayload;
import com.trello.api.models.request.CardPayload;
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
@Feature("Card Operations")
@Story("Update Card")
public class TC_017_UpdateCardNameAndDescTest extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private CardClient cardClient;
    private String tempBoardId;
    private String tempListId;
    private String cardId;
    private boolean isBoardDeleted = false;

    @BeforeClass
    public void setup(ITestContext context) {
        boardClient = new BoardClient();
        listClient = new ListClient();
        cardClient = new CardClient();

        BoardPayload boardPayload = BoardPayload.builder()
                .name("Temp_Board_For_Card_Update")
                .desc("Temporary board for TC_017 card verification")
                .build();

        Response boardResponse = boardClient.createBoard(boardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(boardResponse, 200);
        tempBoardId = boardResponse.jsonPath().getString("id");
        logger.info("PRECONDITION A: Created temporary Board with ID: {}", tempBoardId);

        ListPayload listPayload = ListPayload.builder()
                .name("Temp_List_For_Card_Update")
                .idBoard(tempBoardId)
                .build();

        Response listResponse = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(listResponse, 200);
        tempListId = listResponse.jsonPath().getString("id");
        logger.info("PRECONDITION B: Created temporary List with ID: {}", tempListId);

        CardPayload cardPayload = CardPayload.builder()
                .name("Initial_Card_Name")
                .idList(tempListId)
                .desc("Initial card description")
                .build();

        Response cardResponse = cardClient.createCard(cardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(cardResponse, 200);
        cardId = cardResponse.jsonPath().getString("id");
        logger.info("PRECONDITION C: Created temporary Card with ID: {}", cardId);
    }

    @Test(description = "TC_017 - Verify Card update process")
    @Description("Verify card update process when entering valid new name and description in the input fields and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL) // Critical used to match "High" priority within Allure's standard levels
    public void testUpdateCardNameAndDesc() {
        CardPayload updatePayload = CardPayload.builder()
                .name("Updated Card Name")
                .desc("This card was updated via API")
                .build();

        Response response = cardClient.updateCard(cardId, updatePayload, validRequestSpec);

        ApiAssertions.assertStatusCode(response, 200);

        ApiAssertions.assertFieldEquals(response, "name", "Updated Card Name");
        ApiAssertions.assertFieldEquals(response, "desc", "This card was updated via API");

        logger.info("Successfully updated Card details for Card ID: {}", cardId);
    }

    @AfterClass
    public void teardown() {
        // 5. POST-CONDITION/CLEANUP: Deleting the board cleans up lists and cards inside it
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