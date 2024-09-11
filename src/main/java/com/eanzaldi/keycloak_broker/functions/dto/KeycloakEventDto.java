package com.eanzaldi.keycloak_broker.functions.dto;

import lombok.Data;

@Data
public class KeycloakEventDto {
    private String userId;
    private String type;
}
