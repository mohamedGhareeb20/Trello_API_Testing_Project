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

public class TC_012_ArchivingList_Test extends BaseTest {


    private BoardClient boardClient;
    private ListClient listClient;
    private String tempBoardId;
    private  String tempListId;


    @BeforeClass
    public void setup(ITestContext context) {
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

        ListPayload listPayload=ListPayload.builder()
                .name("Test_List")
                .idBoard(tempBoardId)
                .value("true")
                .build();

        Response listResponse = listClient.createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(listResponse, 200);
        ApiAssertions.assertFieldEquals(listResponse, "name", "Test_List");
        tempListId=listResponse.jsonPath().getString("id");
        String listId = listResponse.jsonPath().getString("id");
        Assert.assertNotNull(listId, "List ID was not generated in the response!");
        context.setAttribute("listId", listId);
        System.setProperty("listId", listId);

        logger.info("Successfully created List with ID: {}", listId);
    }

    @Test(description = "TC_012 - Verify Archive List process")
    @Description("Verify Archiving  list process when selecting a valid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
public void closeList()
{
   Response response= listClient.setListClosed(
            tempListId,
            "true",
            validRequestSpec
    );
    ApiAssertions.assertStatusCode(response, 200);
    ApiAssertions.assertFieldEquals(response, "name", "Test_List");
    ApiAssertions.assertFieldEquals(response, "closed", true);
    ApiAssertions.assertFieldEquals(response, "id", tempListId);

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
