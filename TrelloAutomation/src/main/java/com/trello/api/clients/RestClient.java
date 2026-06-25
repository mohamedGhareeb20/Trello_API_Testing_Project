package com.trello.api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestClient {
    private static final Logger logger = LogManager.getLogger(RestClient.class);

    protected Response get(RequestSpecification spec, String endpoint, Map<String, ?> pathParams, Map<String, ?> queryParams) {
        logger.info("Executing GET request to: {}", endpoint);
        RequestSpecification request = RestAssured.given().spec(spec);
        if (pathParams != null) request.pathParams(pathParams);
        if (queryParams != null) request.queryParams(queryParams);
        return request.get(endpoint);
    }

    protected Response post(RequestSpecification spec, String endpoint, Object body, Map<String, ?> queryParams) {
        logger.info("Executing POST request to: {}", endpoint);
        RequestSpecification request = RestAssured.given().spec(spec);
        if (body != null) request.body(body);
        if (queryParams != null) request.queryParams(queryParams);
        return request.post(endpoint);
    }

    protected Response put(RequestSpecification spec, String endpoint, Object body, Map<String, ?> pathParams, Map<String, ?> queryParams) {
        logger.info("Executing PUT request to: {}", endpoint);
        RequestSpecification request = RestAssured.given().spec(spec);
        if (body != null) request.body(body);
        if (pathParams != null) request.pathParams(pathParams);
        if (queryParams != null) request.queryParams(queryParams);
        return request.put(endpoint);
    }


    protected Response delete(RequestSpecification spec, String endpoint, Map<String, ?> pathParams) {
        logger.info("Executing DELETE request to: {}", endpoint);
        RequestSpecification request = RestAssured.given().spec(spec);
        if (pathParams != null) request.pathParams(pathParams);
        return request.delete(endpoint);
    }
}