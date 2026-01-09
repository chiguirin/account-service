package com.bank.account.infrastructure.client;


import com.bank.account.exception.BusinessException;
import com.bank.account.infrastructure.client.dto.CustomerStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerClient {

    private static final Logger log = LoggerFactory.getLogger(CustomerClient.class);

    private final RestTemplate restTemplate;
    private final String customerServiceUrl;

    public CustomerClient(
            RestTemplate restTemplate,
            @Value("${customer.service.url}") String customerServiceUrl

    ) {
        this.restTemplate = restTemplate;
        this.customerServiceUrl = customerServiceUrl;
    }


    public CustomerStatusDTO getCustomerStatus(String customerId) {
        log.info("Calling customer-service to validate customer {}", customerId);

        try {
            return restTemplate.getForObject(
                    customerServiceUrl + "/clientes/{id}",
                    CustomerStatusDTO.class,
                    customerId
            );
        } catch (Exception ex) {
            log.error("Error calling customer-service for customer {}", customerId, ex);
            throw new BusinessException("Customer not found");
        }
    }
}