package com.trello.api.tests.checklist;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.clients.CardClient;
import com.trello.api.clients.ChecklistClient;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.BoardPayload;
import com.trello.api.models.request.CardPayload;
import com.trello.api.models.request.ChecklistPayload;
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
@Feature("Checklist Operations")
@Story("Create Checklist")
public class TC_021_CreateChecklistTest extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private CardClient cardClient;
    private ChecklistClient checklistClient;

    private String tempBoardId;
    private String tempListId;
    private String tempCardId;
    private boolean isBoardDeleted = false;

    @BeforeClass
    public void setup() {
        boardClient = new BoardClient();
        listClient = new ListClient();
        cardClient = new CardClient();
        checklistClient = new ChecklistClient();

        BoardPayload boardPayload = BoardPayload.builder()
                .name("Temp_Board_For_Checklist")
                .desc("Temporary board for TC_021 checklist verification")
                .build();

        Response boardResponse = boardClient.createBoard(boardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(boardResponse, 200);
        tempBoardId = boardResponse.jsonPath().getString("id");
        logger.info("PRECONDITION A: Created temporary Board with ID: {}", tempBoardId);

        ListPayload listPayload = ListPayload.builder()
                .name("Temp_List_For_Checklist")
                .idBoard(tempBoardId)
                .build();

        Response listResponse = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(listResponse, 200);
        tempListId = listResponse.jsonPath().getString("id");
        logger.info("PRECONDITION B: Created temporary List with ID: {}", tempListId);

        CardPayload cardPayload = CardPayload.builder()
                .name("Temp_Card_For_Checklist")
                .idList(tempListId)
                .desc("Temporary card for TC_021 checklist verification")
                .build();

        Response cardResponse = cardClient.createCard(cardPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(cardResponse, 200);
        tempCardId = cardResponse.jsonPath().getString("id");
        logger.info("PRECONDITION C: Created temporary Card with ID: {}", tempCardId);
    }

    @Test(description = "TC_021 - Verify Checklist creation process")
    @Description("Verify checklist creation process when entering a valid name and selecting a valid Card ID with valid credentials.")
    @Severity(SeverityLevel.NORMAL) // Normal maps to "Medium" priority inside Allure's standard levels
    public void testCreateChecklist(ITestContext context) {
        ChecklistPayload checklistPayload = ChecklistPayload.builder()
                .name("Test_Checklist")
                .idCard(tempCardId)
                .build();

        Response response = checklistClient.createChecklist(checklistPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "name", "Test_Checklist");
        ApiAssertions.assertFieldEquals(response, "idCard", tempCardId);
        String checklistId = response.jsonPath().getString("id");
        Assert.assertNotNull(checklistId, "Checklist ID was not generated in the response!");
        context.setAttribute("checklistId", checklistId);
        System.setProperty("checklistId", checklistId);
        logger.info("Successfully created Checklist with ID: {} on Card ID: {}", checklistId, tempCardId);
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