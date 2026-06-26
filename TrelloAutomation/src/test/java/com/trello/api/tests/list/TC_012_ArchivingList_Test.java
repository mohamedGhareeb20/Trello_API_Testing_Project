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
@Story("Archive List")
public class TC_012_ArchivingList_Test extends BaseTest {

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
                .name("Temp_Board_For_List_Archiving")
                .desc("Temporary board for TC_012 list archiving verification")
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
        ApiAssertions.assertFieldEquals(listResponse, "name", "Test_List");

        tempListId = listResponse.jsonPath().getString("id");
        Assert.assertNotNull(tempListId, "List ID was not generated in the response!");

        context.setAttribute("listId", tempListId);
        System.setProperty("listId", tempListId);

        logger.info("PRECONDITION: Created temporary List with ID: {}", tempListId);
    }

    @Test(description = "TC_012 - Verify Archive List process")
    @Description("Verify list archiving process when selecting a valid List ID in the URL path and entering valid credentials.") // Fixed typo
    @Severity(SeverityLevel.CRITICAL)
    public void closeList() {
        Response response = listClient.setListClosed(
                tempListId,
                "true",
                validRequestSpec
        );

        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "name", "Test_List");
        ApiAssertions.assertFieldEquals(response, "closed", true);
        ApiAssertions.assertFieldEquals(response, "id", tempListId);
        logger.info("Successfully archived List with ID: {}", tempListId);
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