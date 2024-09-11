package com.eanzaldi.keycloak_broker.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class KeycloakFunctions {

    private static final Logger log = LoggerFactory.getLogger(KeycloakFunctions.class);

    @Bean
    public Function<String, String> event() {
        return message -> {
            log.info("Received message headers: {}", message);
            return message;
        };
    }

    @Bean
    public Function<String, String> adminEvent() {
        return message -> {
            log.info("Received admin message headers: {}", message);
            return message;
        };
    }
}
