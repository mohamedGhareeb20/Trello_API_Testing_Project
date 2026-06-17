package com.trello.api.clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MemberClient extends RestClient {
    private static final String MEMBER_ME_ENDPOINT = "/1/members/me";

    public Response getMemberMe(RequestSpecification spec) {
        return get(spec, MEMBER_ME_ENDPOINT, null, null);
    }
}