package org.example.sportsfile;

import org.example.sportsfile.utils.SecurityContextUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    protected MockMvc mockMvc;

    private static final MinIOContainer minioContainer = new MinIOContainer(
            DockerImageName.parse("minio/minio:RELEASE.2024-08-03T04-33-23Z")
    );

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        SecurityContextUtils.mockSecurityContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @BeforeAll
    public static void startContainers() {
        minioContainer.start();
    }

    @AfterAll
    public static void stopContainers() {
        minioContainer.stop();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.s3.endpoint", minioContainer::getS3URL);
        registry.add("spring.s3.access-key", minioContainer::getUserName);
        registry.add("spring.s3.secret-key", minioContainer::getPassword);
        registry.add("spring.s3.bucket", () -> "sports-file");
        registry.add("spring.s3.region", () -> "us-east-1");
    }
}