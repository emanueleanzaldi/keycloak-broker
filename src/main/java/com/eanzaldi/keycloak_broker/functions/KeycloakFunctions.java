package com.eanzaldi.keycloak_broker.functions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
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

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Event event = objectMapper.readValue(message, Event.class);
                log.info("Event parsed: {}", event);
            } catch (JsonProcessingException e) {
                log.error("Error parsing message");
                throw new RuntimeException("Error parsing message: {}", e);
            }
            return message;
        };
    }

    @Bean
    public Function<String, String> adminEvent() {
        return message -> {
            log.info("Received ADMIN message headers: {}", message);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                AdminEvent event = objectMapper.readValue(message, AdminEvent.class);
                log.info("Event parsed: {}", event);
            } catch (JsonProcessingException e) {
                log.error("Error parsing message");
                throw new RuntimeException("Error parsing message: {}", e);
            }
            return message;
        };
    }
}
