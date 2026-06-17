package com.trello.api.clients;

import com.trello.api.models.request.CardPayload;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class CardClient extends RestClient {
    private static final String CARDS_ENDPOINT = "/1/cards";
    private static final String CARD_DETAIL_ENDPOINT = "/1/cards/{id}";

    public Response createCard(CardPayload payload, RequestSpecification spec) {
        Map<String, String> queryParams = new HashMap<>();
        if (payload.getName() != null) queryParams.put("name", payload.getName());
        if (payload.getIdList() != null) queryParams.put("idList", payload.getIdList());
        return post(spec, CARDS_ENDPOINT, null, queryParams);
    }

    public Response updateCard(String cardId, CardPayload payload, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", cardId);
        Map<String, String> queryParams = new HashMap<>();
        if (payload.getName() != null) queryParams.put("name", payload.getName());
        if (payload.getDesc() != null) queryParams.put("desc", payload.getDesc());
        return put(spec, CARD_DETAIL_ENDPOINT, null, pathParams, queryParams);
    }

    public Response getCard(String cardId, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", cardId);
        return get(spec, CARD_DETAIL_ENDPOINT, pathParams, null);
    }
}