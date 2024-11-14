package org.example.sportsgateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SportsGatewayApplicationTests {

    @Test
    void contextLoads() {
        assert(true);
    }

}
