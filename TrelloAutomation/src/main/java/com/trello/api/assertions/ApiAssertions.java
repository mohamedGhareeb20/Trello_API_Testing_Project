package com.trello.api.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public class ApiAssertions {

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Status code mismatch! Expected: " + expectedStatusCode + " but got: " + response.getStatusCode());
    }

    public static void assertBodyContainsText(Response response, String expectedText) {
        String body = response.getBody().asString();
        Assert.assertTrue(body.contains(expectedText),
                "Response body does not contain expected string! Target text: [" + expectedText + "] in body: " + body);
    }

    public static void assertFieldEquals(Response response, String jsonPathExpression, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPathExpression);
        Assert.assertEquals(actualValue, expectedValue,
                "Field validation mismatch at expression: " + jsonPathExpression);
    }
}