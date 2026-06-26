package com.trello.api.clients;

import com.trello.api.models.request.CardPayload;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class CardClient extends RestClient {
    private static final String CARDS_ENDPOINT = "/1/cards";
    private static final String CARD_DETAIL_ENDPOINT = "/1/cards/{id}";

    public Response createCard(CardPayload payload, RequestSpecification spec) {
        // Sends the payload in the HTTP Body to ensure all fields (name, idList, desc) are processed
        return post(spec, CARDS_ENDPOINT, payload, null);
    }

    public Response updateCard(String cardId, CardPayload payload, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", cardId);
        // Sends the payload in the HTTP Body
        return put(spec, CARD_DETAIL_ENDPOINT, payload, pathParams, null);
    }

    public Response getCard(String cardId, RequestSpecification spec) {
        Map<String, String> pathParams = Map.of("id", cardId);
        return get(spec, CARD_DETAIL_ENDPOINT, pathParams, null);
    }
}