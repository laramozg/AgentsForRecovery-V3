package org.example.sportsuser;

import org.example.sportsuser.configurations.PostgresAutoConfiguration;
import org.example.sportsuser.repositories.UserRepository;
import org.example.sportsuser.utils.SecurityContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.example.sportsuser.utils.Models.USER;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest extends PostgresAutoConfiguration {

    protected WebTestClient client;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(ApplicationContext context) {
        createUserIfNotExist();
        client = WebTestClient.bindToApplicationContext(context)
                .apply(SecurityMockServerConfigurers.springSecurity())
                .configureClient()
                .build()
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(SecurityContextUtil.createAuthentication()));
    }

    private void createUserIfNotExist() {
        Mono.justOrEmpty(userRepository.findById(USER.getId()))
                .switchIfEmpty(Mono.defer(() -> Mono.just(userRepository.save(USER))))
                .block();
    }
}