package com.eanzaldi.keycloak_broker.functions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
public class KeycloakFunctions {

    private static final Logger log = LoggerFactory.getLogger(KeycloakFunctions.class);

    @Bean
    public Function<Message<String>, Message<String>> event() {
        return message -> {
            log.info("Received message: {}", message.getPayload());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Event event = objectMapper.readValue(message.getPayload(), Event.class);
                String targetTopic;
                switch (event.getType()) {
                    case REGISTER:
                        targetTopic = "user-register-event";
                        break;
                    case LOGIN:
                        targetTopic = "user-login-event";
                        break;
                    default:
                        log.warn("Unhandled event type: {}", event.getType());
                        return message;  // Restituisci il messaggio senza fare nulla se non è gestito
                }

                // Crea un nuovo messaggio con la destinazione impostata nell'header
                return MessageBuilder.withPayload(message.getPayload())
                        .setHeader("spring.cloud.stream.sendto.destination", targetTopic)
                        .build();

            } catch (JsonProcessingException e) {
                log.error("Error parsing message: {}", e.getMessage());
                throw new RuntimeException("Error parsing message", e);
            }
        };
    }

    @Bean
    public Function<String, String> adminEvent() {
        return message -> {
            log.info("Received ADMIN message headers: {}", message);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                AdminEvent event = objectMapper.readValue(message, AdminEvent.class);
                log.info("Event ADMIN parsed: {}", event);
            } catch (JsonProcessingException e) {
                log.error("Error parsing message");
                throw new RuntimeException("Error parsing message: {}", e);
            }
            return message;
        };
    }
}
