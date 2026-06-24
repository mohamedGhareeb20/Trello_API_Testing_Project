package com.trello.api.tests.list;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.BoardClient;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.BoardPayload;
import com.trello.api.models.request.ListPayload;
import com.trello.api.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC_011_CreateListTest  extends BaseTest {


    private BoardClient boardClient;
    private ListClient listClient;
    private String tempBoardId;


    @BeforeClass
    public void setup() {
        listClient=new ListClient();
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

    @Test(description = "TC_011 - Verify list creation process")
    @Description("Verify create list process when selecting a valid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL) // Critical maps to "High" priority inside Allure's standard levels
    public void CreateListTC(ITestContext context)
    {
        ListPayload listPayload=ListPayload.builder()
                .name("Test_List")
                .idBoard(tempBoardId)
                .value("true")
                .build();

        Response response = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "name", "Test");
        String listId = response.jsonPath().getString("id");
        Assert.assertNotNull(listId, "List ID was not generated in the response!");
        context.setAttribute("boardId", listId);
        System.setProperty("boardId", listId);

        logger.info("Successfully created List with ID: {}", listId);



    }
    @AfterClass
    public void teardown() {
        boolean isDeletedSuccessfully = false;
        if (!isDeletedSuccessfully && tempBoardId != null) {
            logger.warn("Safety teardown executing. Cleaning up un-deleted Board with ID: {}", tempBoardId);
            try {
                boardClient.deleteBoard(tempBoardId, validRequestSpec);
            } catch (Exception e) {
                logger.error("Failed to execute safety board cleanup for ID: " + tempBoardId, e);
            }
        }
    }
}
