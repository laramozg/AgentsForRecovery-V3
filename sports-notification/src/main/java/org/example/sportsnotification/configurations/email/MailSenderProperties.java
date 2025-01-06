package org.example.sportsnotification.configurations.email;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.mail")
public class MailSenderProperties {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String protocol;
    private final String debug;

    public MailSenderProperties(String host, int port, String username, String password, String protocol, String debug) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
        this.debug = debug;
    }

}
