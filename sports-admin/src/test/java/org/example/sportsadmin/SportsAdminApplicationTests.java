package org.example.sportsadmin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class SportsAdminApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

}
