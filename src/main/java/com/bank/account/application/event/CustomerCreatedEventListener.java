package com.bank.account.application.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreatedEventListener {

    private static final Logger log = LoggerFactory.getLogger(CustomerCreatedEventListener.class);

    @EventListener
    public void handleCustomerCreated(CustomerCreatedEvent event) {
        log.info("Customer created event received for id {}", event.customerId());
    }
}