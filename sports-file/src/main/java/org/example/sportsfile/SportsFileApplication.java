package org.example.sportsfile;

import org.example.sportsfile.configurations.s3.S3Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(S3Properties.class)
public class SportsFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsFileApplication.class, args);
    }

}
