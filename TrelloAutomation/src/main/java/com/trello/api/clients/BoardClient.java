package com.trello.api.clients;

import com.trello.api.models.request.BoardPayload;
import com.trello.api.specs.TrelloSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class BoardClient extends RestClient {
    private static final String BOARD_ENDPOINT = "/1/boards/";
    private static final String BOARD_DETAIL_ENDPOINT = "/1/boards/{id}";

    public Response createBoard(BoardPayload payload, RequestSpecification spec) {
        Map<String, String> queryParams = new HashMap<>();
        if (payload.getName() != null) queryParams.put("name", payload.getName());
        if (payload.getDesc() != null) queryParams.put("desc", payload.getDesc());
        return post(spec, BOARD_ENDPOINT, null, queryParams);
    }

    public Response getBoard(String boardId, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", boardId);
        return get(spec, BOARD_DETAIL_ENDPOINT, pathParams, null);
    }

    public Response updateBoard(String boardId, BoardPayload payload, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", boardId);
        Map<String, String> queryParams = new HashMap<>();
        if (payload.getName() != null) queryParams.put("name", payload.getName());
        if (payload.getDesc() != null) queryParams.put("desc", payload.getDesc());
        return put(spec, BOARD_DETAIL_ENDPOINT, null, pathParams, queryParams);
    }

    public Response deleteBoard(String boardId, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", boardId);
        return delete(spec, BOARD_DETAIL_ENDPOINT, pathParams);
    }
}