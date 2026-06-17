package com.trello.api.clients;

import com.trello.api.models.request.ChecklistPayload;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class ChecklistClient extends RestClient {
    private static final String CHECKLIST_ENDPOINT = "/1/checklists";

    public Response createChecklist(ChecklistPayload payload, RequestSpecification spec) {
        Map<String, String> queryParams = new HashMap<>();
        if (payload.getName() != null) queryParams.put("name", payload.getName());
        if (payload.getIdCard() != null) queryParams.put("idCard", payload.getIdCard());
        return post(spec, CHECKLIST_ENDPOINT, null, queryParams);
    }
}