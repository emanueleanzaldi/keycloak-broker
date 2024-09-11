package com.eanzaldi.keycloak_broker.functions.dto;

import lombok.Data;

@Data
public class MessageDto {
    private long time;
    private String realmId;
    private AuthDetailsDto authDetails;
    private String resourceType;
    private String operationType;
    private String resourcePath;
    private String representation;
    private String error;
    private String resourceTypeAsString;
}
