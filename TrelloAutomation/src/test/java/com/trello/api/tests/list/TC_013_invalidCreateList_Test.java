package com.trello.api.tests.list;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.ListClient;
import com.trello.api.models.request.ListPayload;
import com.trello.api.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC_013_invalidCreateList_Test extends BaseTest {



    @Test(description = "TC_013 - Verify in valid list creation process")
    @Description("Verify create list process when selecting a invalid Board ID in the URL path and entering valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateList(ITestContext context) {
        // 2. TEST: Build payload and execute POST request to Trello
        ListPayload listPayload = ListPayload.builder()
                .name("Test_List")
                .idBoard("invalid_id")
                .build();

        Response response = new ListClient().createList(listPayload, validRequestSpec);
        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertBodyContainsText(response,"invalid value for idBoard");


        logger.info("List can't be created  with invalid board Id");
    }
}
