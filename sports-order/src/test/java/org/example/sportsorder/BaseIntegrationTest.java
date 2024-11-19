package org.example.sportsorder;

import org.example.sportsorder.configurations.PostgresAutoConfiguration;
import org.example.sportsorder.utils.SecurityContextMockUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = {PostgresAutoConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        SecurityContextMockUtil.mockSecurityContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }
}