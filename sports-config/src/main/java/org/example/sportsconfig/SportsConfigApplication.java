package org.example.sportsconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class SportsConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsConfigApplication.class, args);
    }

}
