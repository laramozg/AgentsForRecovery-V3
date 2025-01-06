package org.example.sportsfight;

import org.example.sportsfight.configurations.kafka.properties.KafkaTopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class SportsFightApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsFightApplication.class, args);
    }

}
