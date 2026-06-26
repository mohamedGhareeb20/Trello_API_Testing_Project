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
@Story("Archive List - Negative Path")
public class TC_015_ArchiveListInvalidValueTest extends BaseTest {

    private BoardClient boardClient;
    private ListClient listClient;
    private String tempBoardId;
    private String tempListId;
    private boolean isBoardDeleted = false;

    @BeforeClass
    public void setup(ITestContext context) {
        listClient = new ListClient();
        boardClient = new BoardClient();
        BoardPayload payload = BoardPayload.builder()
                .name("Temp_Board_For_List_Archiving_Validation")
                .desc("Temporary board for TC_015 validation")
                .build();
        Response response = boardClient.createBoard(payload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        tempBoardId = response.jsonPath().getString("id");
        logger.info("PRECONDITION: Created temporary Board with ID: {}", tempBoardId);
        ListPayload listPayload = ListPayload.builder()
                .name("Test_List")
                .idBoard(tempBoardId)
                .build();
        Response listResponse = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(listResponse, 200);
        tempListId = listResponse.jsonPath().getString("id");
        Assert.assertNotNull(tempListId, "List ID was not generated in the response!");
        context.setAttribute("listId", tempListId);
        System.setProperty("listId", tempListId);
        logger.info("PRECONDITION: Created temporary List with ID: {}", tempListId);
    }

    @Test(description = "TC_015 - Verify list archiving fails with invalid value type")
    @Description("Verify list archiving process when selecting a valid List ID and entering an invalid data type (string) in the 'closed' parameter.")
    @Severity(SeverityLevel.MINOR) // Aligned with the 'Low' priority in your manual sheet
    public void testArchiveListWithInvalidValueType() {
        Response response = listClient.setListClosed(
                tempListId,
                ";lksdl;a",
                validRequestSpec
        );
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response, "invalid value for value");
        logger.info("Successfully verified that Trello rejects archiving when 'value' is not a boolean.");
    }

    @AfterClass
    public void teardown() {
        if (!isBoardDeleted && tempBoardId != null) {
            logger.info("Teardown executing. Cleaning up temporary Board with ID: {}", tempBoardId);
            try {
                boardClient.deleteBoard(tempBoardId, validRequestSpec);
                isBoardDeleted = true;
            } catch (Exception e) {
                logger.error("Failed to execute safety board cleanup for ID: " + tempBoardId, e);
            }
        }
    }
}