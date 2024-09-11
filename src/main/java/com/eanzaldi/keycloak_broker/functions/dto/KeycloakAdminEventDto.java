package com.eanzaldi.keycloak_broker.functions.dto;

import lombok.Data;

@Data
public class KeycloakAdminEventDto {
    private String realmId;
    private String operationType;
}
