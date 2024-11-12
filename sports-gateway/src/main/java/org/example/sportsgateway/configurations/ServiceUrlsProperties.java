package org.example.sportsgateway.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String sportsUser;
//    private final String sportsOrder;


    public ServiceUrlsProperties(String sportsUser) {
        this.sportsUser = sportsUser;

    }

    public String getSportsUser() {
        return sportsUser;
    }

}