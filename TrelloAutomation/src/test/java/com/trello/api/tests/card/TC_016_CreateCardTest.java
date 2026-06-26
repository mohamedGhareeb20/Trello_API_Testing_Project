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
@Story("Create Card")
public class TC_016_CreateCardTest extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private CardClient cardClient;
    private String tempBoardId;
    private String tempListId;
    private boolean isBoardDeleted = false;

    @BeforeClass
    public void setup(ITestContext context) {
        listClient = new ListClient();
        boardClient = new BoardClient();
        cardClient = new CardClient();

        BoardPayload boardPayload = BoardPayload.builder()
                .name("Temp_Board_For_Card_Creation")
                .desc("Temporary board for TC_016 card verification")
                .build();

        Response boardResponse = boardClient.createBoard(boardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(boardResponse, 200);
        tempBoardId = boardResponse.jsonPath().getString("id");
        logger.info("PRECONDITION A: Created temporary Board with ID: {}", tempBoardId);

        ListPayload listPayload = ListPayload.builder()
                .name("Temp_List_For_Card_Creation")
                .idBoard(tempBoardId)
                .build();

        Response listResponse = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(listResponse, 200);
        tempListId = listResponse.jsonPath().getString("id");
        logger.info("PRECONDITION B: Created temporary List with ID: {}", tempListId);
    }

    @Test(description = "TC_016 - Verify Card creation process")
    @Description("Verify card creation process when entering a valid name and selecting a valid List ID with valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateCard(ITestContext context) {
        // 3. TEST: Build the payload and execute POST request to Trello
        CardPayload cardPayload = CardPayload.builder()
                .name("Test_Card")
                .idList(tempListId)
                .desc("test card for project")
                .build();

        Response response = cardClient.createCard(cardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "name", "Test_Card");
        ApiAssertions.assertFieldEquals(response, "desc", "test card for project");
        String cardId = response.jsonPath().getString("id");
        Assert.assertNotNull(cardId, "Card ID was not generated in the response!");
        context.setAttribute("cardId", cardId);
        System.setProperty("cardId", cardId);
        logger.info("Successfully created Card with ID: {}", cardId);
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