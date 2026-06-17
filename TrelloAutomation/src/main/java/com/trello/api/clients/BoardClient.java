package com.trello.api.clients;

import com.trello.api.models.request.BoardPayload;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class BoardClient extends RestClient {
    private static final String BOARD_ENDPOINT = "/1/boards/";
    private static final String BOARD_DETAIL_ENDPOINT = "/1/boards/{id}";

    public Response createBoard(BoardPayload payload, RequestSpecification spec) {
        // Send payload in the HTTP Body to keep the URL clean
        return post(spec, BOARD_ENDPOINT, payload, null);
    }

    public Response getBoard(String boardId, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", boardId);
        return get(spec, BOARD_DETAIL_ENDPOINT, pathParams, null);
    }

    public Response updateBoard(String boardId, BoardPayload payload, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", boardId);
        // Send payload in the HTTP Body to prevent 414 Request-URI Too Large errors
        return put(spec, BOARD_DETAIL_ENDPOINT, payload, pathParams, null);
    }

    public Response deleteBoard(String boardId, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", boardId);
        return delete(spec, BOARD_DETAIL_ENDPOINT, pathParams);
    }
}