package org.example.sportsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableConfigurationProperties(ServiceUrlsProperties::class)
public class SportsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsGatewayApplication.class, args);
    }

}
