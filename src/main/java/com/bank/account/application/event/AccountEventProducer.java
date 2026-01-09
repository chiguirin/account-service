package com.bank.account.application.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountEventProducer {

    private static final String TOPIC = "account.created";

    private final KafkaTemplate<String, AccountCreatedEvent> kafkaTemplate;

    public AccountEventProducer(KafkaTemplate<String, AccountCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(AccountCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}