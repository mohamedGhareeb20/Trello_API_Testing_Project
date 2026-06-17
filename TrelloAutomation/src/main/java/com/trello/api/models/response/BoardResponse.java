package com.trello.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardResponse {
    private String id;
    private String name;
    private String desc;
    private Boolean closed;
}