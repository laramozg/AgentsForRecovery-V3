package org.example.sportsgateway.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String sportsUser;
    private final String sportsOrder;


    public ServiceUrlsProperties(String sportsUser, String sportsOrder) {
        this.sportsUser = sportsUser;
        this.sportsOrder = sportsOrder;

    }

    public String getSportsUser() {
        return sportsUser;
    }

    public String getSportsOrder() {
        return sportsOrder;
    }
}