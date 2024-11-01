package org.example.sportsorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SportsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsOrderApplication.class, args);
    }

}
