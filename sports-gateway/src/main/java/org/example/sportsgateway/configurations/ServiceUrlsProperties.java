package org.example.sportsgateway.configurations;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String sportsUser;
    private final String sportsOrder;
    private final String sportsFight;
    private final String sportsFile;

    public ServiceUrlsProperties(String sportsUser, String sportsOrder, String sportsFight, String sportsFile) {
        this.sportsUser = sportsUser;
        this.sportsOrder = sportsOrder;
        this.sportsFight = sportsFight;
        this.sportsFile = sportsFile;
    }

}