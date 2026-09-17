package com.bank.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Service registry for all banking microservices. Every downstream
 * service (customer, loan, risk-assessment, e-signature, transaction)
 * registers itself here so that loan-service can discover and call
 * the others by logical name instead of hardcoded host:port.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
