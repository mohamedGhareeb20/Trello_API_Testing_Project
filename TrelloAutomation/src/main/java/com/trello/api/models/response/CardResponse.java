package com.trello.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardResponse {
    private String id;
    private String name;
    private String desc;
    private String idList;
    private String idBoard;
}