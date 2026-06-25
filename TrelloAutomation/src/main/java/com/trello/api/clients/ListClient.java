package com.trello.api.clients;

import com.trello.api.models.request.ListPayload;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class ListClient extends RestClient {
    private static final String LISTS_ENDPOINT = "/1/lists";
    private static final String LIST_CLOSED_ENDPOINT = "/1/lists/{id}/closed";

    public Response createList(ListPayload payload, RequestSpecification spec) {
        Map<String, String> queryParams = new HashMap<>();
        if (payload.getName() != null) queryParams.put("name", payload.getName());
        if (payload.getIdBoard() != null) queryParams.put("idBoard", payload.getIdBoard());
        return post(spec, LISTS_ENDPOINT, null, queryParams);
    }

    public Response setListClosed(String listId, Object closedValue, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", listId);
        Map<String, Object> queryParams = Map.of("value", closedValue);
        return put(spec, LIST_CLOSED_ENDPOINT, null, pathParams, queryParams);
    }
}