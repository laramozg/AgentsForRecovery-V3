package org.example.sportsfight;


import org.example.sportsfight.configurations.PostgresAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.example.sportsfight.utils.AuthenticationUtils.createAuthentication;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PostgresAutoConfiguration.class}
)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    protected WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context) {
        client = WebTestClient.bindToApplicationContext(context)
                .apply(SecurityMockServerConfigurers.springSecurity())
                .configureClient()
                .build()
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(createAuthentication()));
    }
}