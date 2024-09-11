package com.eanzaldi.keycloak_broker.functions.dto;

import lombok.Data;

@Data
public class AuthDetailsDto {
    private String realmId;
    private String clientId;
    private String userId;
    private String ipAddress;
}
