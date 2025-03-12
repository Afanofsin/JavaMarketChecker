package com.marketcheck.marketchecker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

}
