package com.trello.api.tests.auth;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.MemberClient;
import com.trello.api.tests.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("User Authentication")
@Story("Authentication with Valid Credentials")
public class TC_001_AuthSuccessValidCredentials extends BaseTest {
    private MemberClient memberClient;

    @BeforeClass
    public void setup() {
        memberClient = new MemberClient();
    }

    @Test(description = "TC_001 - Verify valid API Key & valid Token")
    @Description("Verify user authentication process when entering a valid API Key and a valid Token in the query parameters.")
    @Severity(SeverityLevel.CRITICAL)
    public void testAuthWithValidCredentials() {
        Response response = memberClient.getMemberMe(validRequestSpec);

        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertFieldEquals(response, "fullName", "Mohamed Ghareeb");
        ApiAssertions.assertFieldEquals(response, "username", "mohamedghareeb26");
    }
}