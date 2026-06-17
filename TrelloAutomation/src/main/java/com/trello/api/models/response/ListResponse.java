package com.trello.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponse {
    private String id;
    private String name;
    private String idBoard;
    private Boolean closed;
}