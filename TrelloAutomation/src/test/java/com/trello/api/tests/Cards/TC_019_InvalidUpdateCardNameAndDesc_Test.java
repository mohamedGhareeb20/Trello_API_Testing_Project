package com.trello.api.tests.Cards;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.clients.CardClient;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.BoardPayload;
import com.trello.api.models.request.CardPayload;
import com.trello.api.models.request.ListPayload;
import com.trello.api.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC_019_InvalidUpdateCardNameAndDesc_Test extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private CardClient cardClient;
    private String tempBoardId;
    private String tempListId;
    private String cardId;
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

    @Test
    public void CreateList(ITestContext context) {
        // 2. TEST: Build payload and execute POST request to Trello
        ListPayload listPayload = ListPayload.builder()
                .name("Test_List")
                .idBoard(tempBoardId)
                .build();

        Response response = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        tempListId=response.jsonPath().getString("id");
    }
    @Test
    public void createCard()
    {
        cardClient=new CardClient();
        CardPayload cardPayload=CardPayload.builder()
                .name("Test_Card")
                .idList(tempListId)
                .desc("test card for project")
                .build();
        Response response=cardClient.createCard(cardPayload,validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response,"name","Test_Card");
        ApiAssertions.assertFieldEquals(response,"desc","test card for project");
        cardId = response.jsonPath().getString("id");
        logger.info("Successfully created Card with ID: {}", cardId);

    }
    @Test(description = "TC_018 - Verify invalid Card update process",dependsOnMethods = {"CreateList","createCard"})
    @Description("Verify update Card process when selecting a  valid card id in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void updateCardNameAndDesc()
    {
        cardClient =new CardClient();
        CardPayload cardPayload=CardPayload.builder()
                .name("new name")
                .desc("new description")
                .build();
        Response response=cardClient.updateCard(cardId+"fakeID",cardPayload,validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response,"invalid id");


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
