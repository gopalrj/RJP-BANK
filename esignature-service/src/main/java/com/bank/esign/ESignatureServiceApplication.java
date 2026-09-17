package com.bank.esign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ESignatureServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ESignatureServiceApplication.class, args);
    }
}
