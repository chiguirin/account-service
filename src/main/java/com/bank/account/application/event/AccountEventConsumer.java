package com.bank.account.application.event;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventConsumer {

    @KafkaListener(
            topics = "account.created",
            groupId = "account-group"
    )
    public void consume(AccountCreatedEvent event) {
        System.out.println(
                "Account created event received. AccountNumber=" + event.getAccountNumber()
        );
    }
}