package com.trello.api.tests.auth;

import com.trello.api.assertions.ApiAssertions;
import com.trello.api.clients.MemberClient;
import com.trello.api.specs.TrelloSpecBuilder;
import com.trello.api.tests.BaseTest;
import com.trello.api.utils.ConfigManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Trello API Test Suite")
@Feature("User Authentication")
@Story("Authentication with Invalid Token")
public class TC_002_AuthFailInvalidToken extends BaseTest {
    private MemberClient memberClient;

    @BeforeClass
    public void setup() {
        memberClient = new MemberClient();
    }

    @Test(description = "TC_002 - Verify valid API Key & invalid/expired Token")
    @Description("Verify user authentication process when entering a valid API Key and an invalid or expired Token in the query parameters.")
    @Severity(SeverityLevel.CRITICAL)
    public void testAuthWithInvalidToken() {
        RequestSpecification invalidTokenSpec = TrelloSpecBuilder.getInvalidRequestSpec(
                ConfigManager.get("valid_key"),
                ConfigManager.get("invalid_token")
        );

        Response response = memberClient.getMemberMe(invalidTokenSpec);
        ApiAssertions.assertStatusCode(response, 401);
        ApiAssertions.assertBodyContainsText(response, "invalid app token"); // Updated here
    }
}