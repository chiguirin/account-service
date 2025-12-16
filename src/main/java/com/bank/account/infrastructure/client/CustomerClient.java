package com.bank.account.infrastructure.client;


import com.bank.account.exception.BusinessException;
import com.bank.account.infrastructure.client.dto.CustomerStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerClient {

    private static final Logger log = LoggerFactory.getLogger(CustomerClient.class);

    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerStatusDTO getCustomerStatus(String customerId) {

        try {
            log.info("Calling customer-service to validate customer {}", customerId);

            return restTemplate.getForObject(
                    "http://customer-service:8080/clientes/{id}",
                    CustomerStatusDTO.class,
                    customerId
            );

        } catch (Exception ex) {
            log.error("Error calling customer-service for customer {}", customerId);
            throw new BusinessException("Customer not found");
        }
    }
}