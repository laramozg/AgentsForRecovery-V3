package org.example.sportsgateway.configurations;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String sportsUser;
    private final String sportsOrder;


    public ServiceUrlsProperties(String sportsUser, String sportsOrder) {
        this.sportsUser = sportsUser;
        this.sportsOrder = sportsOrder;

    }

}